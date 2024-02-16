package com.Sumitav.Services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Sumitav.Entity.User;
import com.Sumitav.Repository.UserRepository;

@Service
public class UserSecurityServiceImpl implements UserDetailsService{

	   @Autowired
	    private UserRepository userRepository;

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


	    	User user=this.userRepository.findByEmail(username);

	        if(user==null)
	        {
	            System.out.println("User Not Found");
	            throw new UsernameNotFoundException("No User Found God!");
	        }
	         return user;

}
}