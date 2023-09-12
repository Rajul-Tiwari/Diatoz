package com.diatoz.jwt;

import com.diatoz.service.UserModelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserModelServiceImpl userModelServiceImpl;

    private String parseToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");


        if(header != null && header.startsWith("Bearer ")){
            return header.substring(7, header.length());
        }


        return null;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{
            String token = parseToken(request);
            if(token !=null && jwtUtil.verifyToken(token)){
                String username  = jwtUtil.getUserNameFromToken(token);
                UserDetails userDetails =  userModelServiceImpl.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken
                        authenticationToken =  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);


            }

        }catch (Exception e){
            System.out.println("Cannot set user authentication: {}"+ e);
        }

        filterChain.doFilter(request, response);

    }
}
