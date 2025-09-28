package com.sps.electronic.store.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestHeader = request.getHeader("Authorization");
        //jwt token always start with Bearer 78jkjd738i8d9.... like this
        logger.info(" Header : {}", requestHeader);

        String username = null;
        String token = null; //set in token our requestHeader

        if (requestHeader != null && requestHeader.startsWith("Bearer")){
            //looking good
            token = requestHeader.substring(7);
            try{
                //if exception not occure then
                username = this.jwtHelper.getUsernameFromToken(token);

            }catch (IllegalArgumentException e){
                logger.info("Illegal argument while fetching the username!!");
                e.printStackTrace();

            }catch (ExpiredJwtException e){
                logger.info("Given jwt token is expired");
                e.printStackTrace();

            }catch (MalformedJwtException e){
                logger.info("some changes has done in token!! Invalid token ");
            }catch (Exception e){
                //to handle other than above three exception
            }
        }else {
            logger.info("Invalid Header Value !!");
        }

        //username is not null means username login karne wala hai
        //aur securityContext me authetication set nahi hai
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

            //fetch user details from username
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);

            if (validateToken){
                //set the authentication
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            }else {
                logger.info("validation fails !!");
            }
        }

        filterChain.doFilter(request, response);
    }
}
