package com.Sumitav.Config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {

	
	public static final long JWT_TOKEN_VALIDITY= 5*60*60;
	
	private String SECRET_KEY = "104e2b105bd7c66a1929ac98af07c6e608386aad4879bcb84d9aa4fcb0f2d592d6b963edc456697afde9d9de542391654c3249e32c100eb6a1a7ad6cfb1daf64";

    public String getUsernameFromToken(String token) {
        return  getClaimFromToken(token, Claims::getSubject); //extractClaim
    }

    public Date  getExpirationDateFromToken(String token) {
        return   getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T   getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims =  getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    private Claims  getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration= getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return  doGenerateToken(claims, userDetails.getUsername());
    }

    private String  doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000  ))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username =  getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
