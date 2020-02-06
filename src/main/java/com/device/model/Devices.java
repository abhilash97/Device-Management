package com.device.model;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Devices {
    private int device_id;
    private String type;
    private String platform;
    private Timestamp timestmap;

    public int getDevice_id() {
        return device_id;
    }
    public String getType(){
        return type;
    }
    public String getPlatform(){
        return platform;
    }
    public Timestamp getTimestmap(){
        return timestmap;
    }
}

