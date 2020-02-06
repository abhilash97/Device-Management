package com.vmware.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
public class User {
    private int usrid;
    private String name;
    private String deptname;
    private Timestamp timestmap;
    private UserObject credentials;

    public User(int usrid, String name, String deptname, Timestamp datetimez) {
        this.usrid=usrid;
        this.name=name;
        this.deptname=deptname;
        this.timestmap=datetimez;
    }
    public User(){

    }
//    public User(int usrid, String name, String deptname, Timestamp datetimez, UserObject creds){
//        this.usrid=usrid;
//        this.name=name;
//        this.deptname=deptname;
//        this.timestmap=datetimez;
//        this.credentials = creds;
//    }

    public int getUsrid(){
        return usrid;
    }

    public java.lang.String getName() {
        return name;
    }

    public String getDeptname(){
        return deptname;
    }

    public UserObject getCredentials(){
        return credentials;
    }
}

