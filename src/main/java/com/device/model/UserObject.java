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
}
