package com.diatoz.jwt;

import com.diatoz.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JwtUtil {
    private  final String jwtSecret = "secret";

    private  final int jwtExpirationInMs = 86400000;

    /**
     * Creates a JWT token based on the user authentication.
     *
     * @param authentication the authentication object
     * @return the generated JWT token
     */
    public String createtoken(Authentication authentication) {
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        String token =   Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+jwtExpirationInMs ))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();


        return token;
    }

    /**
     * Verifies the validity of a JWT token.
     *
     * @param token the JWT token to verify
     * @return true if the token is valid, false otherwise
     * @throws SignatureException       if the JWT signature is invalid
     * @throws MalformedJwtException    if the JWT token is malformed
     * @throws UnsupportedJwtException  if the JWT token is unsupported
     * @throws IllegalArgumentException if the JWT claims string is empty
     */
    public boolean verifyToken(String token) {

        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }
        catch (SignatureException e){
            throw new SignatureException("Invalid JWT signature");
        }
        catch (MalformedJwtException e){
            throw new MalformedJwtException("Invalid JWT token");
        }
        catch(UnsupportedJwtException e){
            throw new UnsupportedJwtException("Unsupported JWT token");
        }
        catch(IllegalArgumentException e){
            throw new IllegalArgumentException("JWT claims string is empty");
        }

    }


    /**
     * Retrieves the username from a JWT token.
     *
     * @param token the JWT token
     * @return the username extracted from the token
     */
    public String getUserNameFromToken(String token) {
        return Jwts.parser().
                setSigningKey(jwtSecret).
                parseClaimsJws(token).
                getBody().getSubject();
    }
}
