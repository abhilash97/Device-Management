package com.vmware.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class UserDeviceAssignment {

    private int device_id;
    private int usrid;
    private Timestamp lastupdate;
    private boolean assigned;

    public int getDevice_id(){
        return device_id;
    }
    public int getUsrid(){
        return usrid;
    }
    public Timestamp getLastupdate(){
        return lastupdate;
    }
    public boolean isAssigned(){
        return assigned;
    }
}
