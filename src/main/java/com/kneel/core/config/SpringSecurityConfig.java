package com.kneel.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.kneel.core.config.handler.KneelAccessDeniedHandler;
import com.kneel.core.service.KneelUserDetailsService;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private KneelUserDetailsService kneelUserDetailsService;

	@Autowired
	private KneelAccessDeniedHandler accessDeniedHandler;

	/**
	 * 配置user-detail服务
	 * 
	 * hasIpAddress: 如果请求来自给定IP地址的话，就允许访问 rememberMe:
	 * 如果用户是通过Remember-me功能认证的，就允许访问
	 * requiresChannel().antMatchers("/admin/info").requiresSecure():
	 * 强制使用https加密.(requiresInsecure)
	 * 
	 * 
	 * 
	 * @param auth
	 * @throws Exception
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		//1. 基于内存的用户存储、认证
//		auth.inMemoryAuthentication().withUser("admin").password("admin")
//				.authorities("ROLE_ADMIN", "ROLE_USER").and().withUser("user")
//				.password("user").authorities("ROLE_USER");
		//2. 基于UserDetailsService用户存储、认证
		auth.userDetailsService(kneelUserDetailsService); 

	}

	/**
	 * 拦截请求
	 * 
	 * spring 默认会启动CRSF拦截,如果请求中的token与服务器不匹配，那么失败.
	 * 
	 * <input type="hidden" name="${_csrf.parameterName}"
	 * value="${_csrf.token}"/>
	 * 
	 * csrf().disable(): 禁用CRSF.
	 * 
	 * 记住我，用户不希望每次都输入用户名密码，那么使用记住我功能，并设置cookie存活时间.
	 * rememberMe().key("abc").rememberMeParameter
	 * ("remember_me").rememberMeCookieName
	 * ("my-remember-me").tokenValiditySeconds(86400)；
	 * 
	 * 
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/register","/user/register","/user/logout").permitAll()//注册页面可以访问
				.antMatchers("/css/**","/js/**","/webjars/**").permitAll()//默认css,js可以访问 
				.antMatchers("/admin/**").hasRole("ADMIN")//admin下面， 必须角色管理员才能访问.
				.antMatchers("/user/**").hasAnyRole("ADMIN","USER")//user下面， 必须角色 用户才能访问  
				.anyRequest().authenticated()//所有请求必须认证后才能访问
				.and().formLogin().loginPage("/login").failureUrl("/login?error").permitAll()//定义login page,并且login Page 可以任意访问
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/login?logout").permitAll()//定义logout page,并且logout page可以任意访问
				.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);//定义没有权限访问处理跳转页面403.
	}
}
