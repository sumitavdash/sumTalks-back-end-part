 package com.Sumitav.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//import com.Sumitav.Services.Impl.UserSecurityServiceImpl;

@Configuration
 
public class SecurityConfig   {
	
	@Autowired
    private  JwtAuthenticationFilter jwtAuthenticationFilter;
	
    @Autowired
    private JwtEntryPointAuthentication unauthorizedHandler;
    
//    @Autowired
//    private  UserSecurityServiceImpl userSecurityServiceImp;
       @Autowired
        private UserDetailsService userDetailsService;

       
       @Autowired
       private  BCryptPasswordEncoder passwordEncoder;

    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    	
    	http.csrf(csrf -> csrf.disable())
    	            
    	          .cors(cors->cors.disable())
    	          .authorizeHttpRequests(
    	        		  auth ->
    	        		           auth.requestMatchers("/home/**").authenticated()
    	        		            .requestMatchers("/auth/generate-token").permitAll()
    	        		            .requestMatchers("/api/user/**").permitAll()
    	        		            .requestMatchers(HttpMethod.OPTIONS).permitAll()
    	        		              .anyRequest().authenticated())
    	                                
    	          .exceptionHandling(ex-> ex.authenticationEntryPoint(unauthorizedHandler))
    	          .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    	         
    	          ;
    	
    	http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);  
    	           
    	return http.build();   
    	}
    
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
    	DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    	daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    	daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
    	return daoAuthenticationProvider;
    }
}

     