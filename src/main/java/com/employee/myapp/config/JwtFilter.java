package com.employee.myapp.config;

import com.employee.myapp.services.CustomUserDetailService;
import com.employee.myapp.utilities.JWTUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JWTUtility jwtUtility;

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String Auth = request.getHeader("Authorization");
        String token=null;
        String username=null;
        if(Auth != null && Auth.startsWith("Bearer ")){
            token=Auth.substring(7);
            username=jwtUtility.extractUsername(token);
        }

        if(username !=null && SecurityContextHolder.getContext().getAuthentication() ==null){
            UserDetails userDetails =customUserDetailService.loadUserByUsername(username);
            if(jwtUtility.validateToken(username,userDetails,token)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request,response);

    }
}
