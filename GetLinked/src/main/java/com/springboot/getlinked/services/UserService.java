package com.springboot.getlinked.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.getlinked.model.Professional;
import com.springboot.getlinked.model.User;
import com.springboot.getlinked.repository.ProfessionalDao;
import com.springboot.getlinked.repository.UserDao;


@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ProfessionalDao professionalDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void register(User user) {
		user.setRole("ROLE_USER");
		userDao.save(user);
	}
	//invoking when a user try to login
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		String t =email.split(":")[1];  
		if(t.equals("user"))	{
			User user = userDao.findByEmail(email.split(":")[0].toString());
			if(user == null) {
				return null;
			}
			
			List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole());
			
			String password = user.getPassword();
			
			return new org.springframework.security.core.userdetails.User(email, password, auth);
		}	else	{
			Professional user = professionalDao.findByEmail(email.split(":")[0].toString());
			if(user == null) {
				return null;
			}
			
			List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRole());
			
			String password = user.getPassword();
			
			return new org.springframework.security.core.userdetails.User(email, password, auth);
		}
	}
	
	public User getByEmail(String email) {
		return userDao.findByEmail(email);
	}
	
}

