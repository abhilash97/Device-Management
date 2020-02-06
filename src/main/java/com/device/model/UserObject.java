package com.device.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserObject {
    private int usrid;
    private String username;
    private String password;

//    public UserObject(int u,String username, String password) {
//        this.usrid=u;
//        this.username=username;
//        this.password=password;
//    }

    public UserObject(){

    }

    public int getUsrid() {
        return usrid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    public void setUsername(String usr){
        this.username= usr;
    }
    public void setPassword(String usr){
        this.password= usr;
    }
}
