package com.device.Dao;

import com.device.model.Devices;
import com.device.model.User;
import com.device.model.UserDeviceAssignment;

import java.util.HashMap;
import java.util.List;

/**
 * interface for database object access for devices db table
 * */

public interface DeviceRepository {

    List<UserDeviceAssignment> getDeviceAssignemnt();
    String insertDevice(Devices device);
    List<Devices> updateDevice(int id, Devices device);
    List<Devices> getDevices();
    List<Devices> deleteDevice(int id);
    String assign(int user_id, Devices device);
    String unAssign(int user_id, int device);
    HashMap<Integer,Integer> devicesPerUser(int id);
    List<User> getDeviceHistory(int device_id);
    HashMap<String,Integer> devicesPerDept(String dept_id);
}
