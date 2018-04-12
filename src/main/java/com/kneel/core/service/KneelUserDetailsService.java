package com.kneel.core.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kneel.core.entity.SysPrivilege;
import com.kneel.core.entity.SysRole;
import com.kneel.core.entity.SysUser;
import com.kneel.core.entity.repository.SysUserRepository;

@Service
public class KneelUserDetailsService implements UserDetailsService {

	@Autowired
	private SysUserRepository sysUserRepository;
 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		SysUser user = sysUserRepository.findByName(username); 
		if (user == null) {
			throw new UsernameNotFoundException("User Name can't be found!");
		}
		return new org.springframework.security.core.userdetails.User(
		          user.getName(), user.getPassword(), user.getActive().equalsIgnoreCase("Y"), true, true, 
		          true, getAuthorities(user.getSysRoles()));
	}

	/**
	 * vis roles to get Authority.
	 * 
	 * @param roles
	 * @return
	 */
	private Collection<? extends GrantedAuthority> getAuthorities(Collection<SysRole> roles) {
		return getGrantedAuthorities(getPrivileges(roles));
	}

	/**
	 * get privileges from roles
	 * 
	 * @param roles
	 * @return
	 */
	private List<String> getPrivileges(Collection<SysRole> roles) {

		List<String> privileges = new ArrayList<>();
		List<SysPrivilege> collection = new ArrayList<>();
		for (SysRole role : roles) {
			privileges.add(role.getName());//role
			collection.addAll(role.getSysPrivileges());//role privileges
		}
		for (SysPrivilege item : collection) {
			privileges.add(item.getName());
		}
		return privileges;
	}

	/**
	 * translate privileges to Granted Authority.
	 * 
	 * @param privileges
	 * @return
	 */
	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

}
