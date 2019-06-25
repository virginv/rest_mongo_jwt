package com.base.mongo.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.base.mongo.models.User;
import com.base.mongo.models.UserPrincipal;
import com.base.mongo.repository.IUserRepository;

/**
 * Class to tell Spring where our user data is located and where to find the information it needs for authentication
 * @author virginia
 *
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private IUserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.info("<< loadUserByUsername " + username);
		User usuario = userRepository.findByUsername(username);
		
		if(usuario == null) {
	      throw new UsernameNotFoundException("User not found");
	    }
		LOGGER.info("loadUserByUsername >>");
	    return UserPrincipal.create(usuario);
		
	}
	
	
}
