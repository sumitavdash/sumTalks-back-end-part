package com.Sumitav.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class AppConfig {
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails userDetails= User.builder().
//				username("devsumitav@gmail.com").password(passwordEncoder().encode("Sumit@1234"))
//				.roles("ADMIN").
//				build();
//		return new InMemoryUserDetailsManager(userDetails);
//	}
	
	@Bean
	public  BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) 
			                                                                               throws Exception{
		return builder.getAuthenticationManager();
	}

}
