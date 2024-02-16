package com.Sumitav.Controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Sumitav.Config.JwtUtils;
import com.Sumitav.Entity.JwtRequest;
import com.Sumitav.Entity.JwtResponse;
import com.Sumitav.Entity.User;
import com.Sumitav.Services.Impl.UserSecurityServiceImpl;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticateController {
       
	@Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserSecurityServiceImpl userSecurityServiceImp;

	
	
	@Autowired
	private UserDetailsService userDetailsService;
	
    @Autowired
    private JwtUtils jwtUtils;
    
    
    private Logger logger = LoggerFactory.getLogger(AuthenticateController.class);

    // Endpoint for generating a JWT token
    @PostMapping("/generate-token")
    public ResponseEntity<JwtResponse> generateToken(@RequestBody JwtRequest jwtRequest)   {
             // Authenticate the user credentials
            this.doAuthenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
 

        // If authenticated, load the user details and generate the token
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        String token = this.jwtUtils.generateToken(userDetails);

        // Return the JWT token in the response body
 
        JwtResponse jwtResponse = new JwtResponse(token, userDetails.getUsername());
        return new ResponseEntity<>(jwtResponse,HttpStatus.OK);
    }

     
    private void doAuthenticate(String email, String password) {
    	UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(email,password);
    	try {
    		authenticationManager.authenticate(authentication);
    	} catch (BadCredentialsException e) {
    		throw new BadCredentialsException("Invalid Username and Password !!");
    	}
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
    	return "Credentials Invalid !!";
    }

    //returns the details of current user
    @GetMapping("/current-user")
    public User getCurrentUser(Principal principal){

    	return ((User) this.userSecurityServiceImp.loadUserByUsername(principal.getName()));
    }
}
