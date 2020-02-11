package com.device.controller;

import com.device.Security.JwtToken;
import com.device.Services.UserDetail;
import com.device.Services.MyUserDetailService;
import com.device.model.UserObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@ComponentScan(basePackages = {"com.device.*"})
public class UserAuthenticationController {

    @Autowired
    private JwtToken jToken;

    @Autowired
    private UserDetail dsrv;

    @Autowired
    private MyUserDetailService usv;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value="/authenticate",method=RequestMethod.POST)
    public String loginAuth(@RequestBody UserObject usrname){
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usrname.getUsername(), usrname.getPassword()));
            if(authenticate.isAuthenticated()){
                //SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(usrname.getUsername(), usrname.getPassword(), new ArrayList<>()));
                return jToken.generateToken(usv.loadUserByUsername(usrname.getUsername()));
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        return "Authentication Failed";
    }
}
