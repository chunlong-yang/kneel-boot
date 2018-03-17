package com.kneel.core.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

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
		// 基于内存的用户存储、认证
		auth.inMemoryAuthentication().withUser("admin").password("admin")
				.authorities("ROLE_ADMIN", "ROLE_USER").and().withUser("user")
				.password("user").authorities("ROLE_USER");

		// 基于数据库的用户存储、认证
		// auth.jdbcAuthentication()
		// .dataSource(dataSource)
		// .usersByUsernameQuery(
		// "select account,password,true from user where account=?")
		// .authoritiesByUsernameQuery(
		// "select account,role from user where account=?");
		// 基于UserDetailsService用户存储、认证
		// auth.userDetailsService(securityUserDetailsService);

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
		http.authorizeRequests().antMatchers("/css/**", "/js/**").permitAll()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/user/**").hasRole("USER")
				.and().formLogin().loginPage("/login").permitAll()//自定义login page
				.and().logout().permitAll()
				.and().formLogin().failureUrl("/login?error")
				.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
	}
}
