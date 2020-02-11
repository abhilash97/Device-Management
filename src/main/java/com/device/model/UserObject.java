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

    public UserObject(){
    }

    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public int getUsrid(){
        return usrid;
    }
    public void setUsrid(int usrid){
        this.usrid = usrid;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
}
