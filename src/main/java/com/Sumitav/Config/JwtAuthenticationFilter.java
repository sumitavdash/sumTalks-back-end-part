package com.Sumitav.Config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Sumitav.Services.Impl.UserSecurityServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter {
  
	private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);
	
	@Autowired
    private UserSecurityServiceImpl userSecurityServiceImp;

    @Autowired
    private JwtUtils jwtUtils;
    
@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");
        System.out.println(requestTokenHeader);
        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
            	//getUsernameFomToken
                username = this.jwtUtils. getUsernameFromToken(jwtToken);
            } catch (ExpiredJwtException e) {
                e.printStackTrace();
                System.out.println("Jwt token has been expired");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error");
            }
        } else {
            System.out.println("Invalid Token");
        }
        //validate condition
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            final UserDetails userDetails = this.userSecurityServiceImp.loadUserByUsername(username);
            if (this.jwtUtils.validateToken(jwtToken, userDetails)) {
                //token is validated
                UsernamePasswordAuthenticationToken usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentication);
            }
        } else {
            System.out.println("Token is not valid");
        }
        filterChain.doFilter(request,response);
    }

 

}
