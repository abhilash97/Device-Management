package com.device.Services;

import com.device.model.UserObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MyUserDetailService implements UserDetailsService {

//    private static List<UserObject> usrlist = new ArrayList();
//
//    public MyUserDetailService() {
//        usrlist.add(new UserObject("falcon", "1234"));
//        usrlist.add(new UserObject("Mneog", "234"));
//    }
    @Autowired
    private JdbcTemplate jdbc;

    String qry = "SELECT * FROM usercreds";

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<UserObject> ls = jdbc.query(qry,(rs, row)->new UserObject(rs.getInt("usrid"),rs.getString("username"),rs.getString("password")));
        for(UserObject usr:ls){
            if(usr.getUsername().equalsIgnoreCase(s)){
                return new UserDetail(s,usr.getPassword());
            }
        }
//        Optional<UserObject> user = ls.stream().filter(u -> u.getUsername().equals(s)).findAny();
//        if(!user.isPresent()) {
//            System.out.println("User not found:" + s);
//            throw new UsernameNotFoundException("User not found by name: " + s);
//        }
        System.out.println("User not found");
        throw new UsernameNotFoundException("User not found exception");
    }
//
//    public UserDetails toUserDetails(UserObject usr){
//        return User.withUsername(usr.getUsername()).password(usr.getPassword()).build();
//    }

}
