package com.device.Security;

import com.device.Services.MyUserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
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
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtToken jwtTokenUtil;

    @Autowired
    private MyUserDetailService msd;
//
//    public AuthenticationFilter(AuthenticationManager authenticationManager) {
//        //super();
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //System.out.println(request.getHeader("Authorization"));
        String tok = request.getHeader("Authorization");
        String jwttoken=null;String usrname=null;
        if (tok != null && tok.startsWith("Bearer ")) {
                jwttoken = tok.substring(7);
                try {
                    usrname = jwtTokenUtil.getUsernameFromToken(jwttoken);
                }
                catch (IllegalArgumentException ie) {
                    System.out.println("token retrieval failed");
                }
                catch (ExpiredJwtException e) {
                    System.out.println("Token expired");
                }
            }
        else{
            System.out.println("Token does not start with bearer");
        }
        if (usrname != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = msd.loadUserByUsername(usrname);

            // if token is valid configure Spring Security to manually set
            // authentication
            if (jwtTokenUtil.validateToken(jwttoken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        //SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("falcon","falcon", new ArrayList<>()));
        chain.doFilter(request,response);
    }
}
