package com.vmware.Security;

import com.vmware.Services.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.Security;
import java.util.ArrayList;

@Configuration
@ComponentScan(basePackages = {"com.vmware.*"})
//@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailService usd;

    @Autowired
    private AuthenticationFilter auth;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.authorizeRequests().antMatchers("/").permitAll();
        //http.authorizeRequests().anyRequest().authenticated().and().addFilter(new AuthenticationFilter(authenticationManager()));
        /**any requests coming to this server has to hold authorization component
        * by enabling the web security the framework considers this for every api
         * */
        http.authorizeRequests().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**").permitAll();
        http.authorizeRequests().antMatchers("/user").permitAll();
        http.authorizeRequests().antMatchers("/authenticate").permitAll().anyRequest().authenticated()
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //http.addFilter(new AuthenticationFilter(authenticationManager()));
        http.addFilterBefore(auth, UsernamePasswordAuthenticationFilter.class);

        //.anyRequest().authenticated();
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usd);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager auth() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public String str(){
        return new String();
    }
//    @Bean
//    public AuthenticationManager auth2(){
//        return new AuthenticationManager() {
//            @Override
//            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//                //System.out.println(authentication.getCredentials().toString()+" "+authentication.getPrincipal().toString());
//                if(authentication.getPrincipal().toString().equals("falcon") && authentication.getCredentials().toString().equals("1234")){
//                    Authentication auth = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
//                            authentication.getCredentials(), new ArrayList<>());
//                    return auth;
//                }
//                authentication.setAuthenticated(false);
//                System.out.println("Failed Authentication");
//                return authentication;
//            }
//        };
//    }
}
