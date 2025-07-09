package com.example.demo.config;

import com.example.demo.auth.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.io.IOException;

//do HTTP request filter
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");//check the header key is it Authorization
        if (header != null && header.startsWith("Bearer ")) {//check the header is it start with bearer
            String token = header.substring(7);//get the JWT token first part
            try {
                // Validate the JWT token
                Claims claims = Jwts.parserBuilder()//ready to read
                        .setSigningKey(JwtUtil.KEY)//check is it match with the key(last part of token)
                        .build()//finish read the parser
                        .parseClaimsJws(token)//check the signature is it match key, and not expired
                        .getBody();//get the content(key pair )


                // You can set the user details in the security context here
                String username = claims.getSubject();//get the token username
                UsernamePasswordAuthenticationToken authentication =//To check the token identity
                        new UsernamePasswordAuthenticationToken(username, null, null);//Attach extra request details (like IP, session info) to the authentication
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));// Store this authentication info in Spring's SecurityContext for this request

                SecurityContextHolder.getContext().setAuthentication(authentication);//Set it as valid, able to access

            } catch (Exception e) {
                // Token invalid or expired
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }
        //Passes the request to the next filter in the filter chain, or to the controller (if this is the last filter).
        //To continue check
        chain.doFilter(request, response);//
    }
}
