package com.vmware.Services;

import com.vmware.Dao.DeviceRepository;
import com.vmware.model.Devices;
import com.vmware.model.User;
import com.vmware.model.UserDeviceAssignment;
import com.vmware.model.UserObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DeviceService implements DeviceRepository {

    @Autowired
    private JdbcTemplate jdbc;

    public void updates(String sql,Devices device){
        jdbc.update(sql,device.getDevice_id(),device.getType(),device.getPlatform(),new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public List<UserDeviceAssignment> getDeviceAssignemnt() {
        String sql = "SELECT * FROM userdevice";
        return jdbc.query(sql,(rs, rowNum)->new UserDeviceAssignment(rs.getInt("device_id"),
                rs.getInt("usrid"),rs.getTimestamp("lastupdate"),rs.getBoolean("assigned")));
    }

    @Override
    public String insertDevice(Devices device) {
        String sqlA = "SELECT * FROM devices";
        String sql = "INSERT INTO devices(device_id, devtype, platform, usrtime) VALUES(?,?,?,?)";
        updates(sql,device);
        return "New Device Created";
    }

    @Override
    public List<Devices> updateDevice(int id, Devices device) {
        String sqlA="SELECT * FROM devices";
        String sql = "UPDATE devices SET device_id = ?, devtype = ?, platform = ?, usrtime = ? WHERE device_id=?";
        updates(sql,device);
        return jdbc.query(sqlA,(rs, rowNum)->new Devices(rs.getInt("device_id"),rs.getString("devtype"),
                rs.getString("platform"),rs.getTimestamp("usrtime")));
    }

    @Override
    public List<Devices> getDevices() {
        String sql="SELECT * FROM devices";
        return jdbc.query(sql,(rs, rowNum)->new Devices(rs.getInt("device_id"),rs.getString("devtype"),
                rs.getString("platform"),rs.getTimestamp("usrtime")));
    }

    @Override
    public List<Devices> deleteDevice(int id) {
        String sqlA = "SELECT * FROM devices";
        String sql = "DELETE FROM devices WHERE devices.device_id=?";
        jdbc.update(sql,id);
        return jdbc.query(sql,(rs, rowNum)->new Devices(rs.getInt("device_id"),rs.getString("devtype"),
                rs.getString("platform"),rs.getTimestamp("usrtime")));
    }

    @Override
    public String assign(int user_id, Devices device) {
        String sql1 = "SELECT * FROM userdevice WHERE device_id=? AND assigned=?";
        String sql2 = "INSERT INTO userdevice(device_id, usrid, lastupdate, assigned) VALUES(?,?,?,?)";
        String sql3 = "UPDATE userdevice SET assigned = ?, lastupdate=? WHERE device_id=? AND usrid=?";
        boolean f1=true;
        List<UserDeviceAssignment> dev = jdbc.query(sql1,(rs, row)->new UserDeviceAssignment(rs.getInt("device_id"),
                rs.getInt("usrid"),
                rs.getTimestamp("lastupdate"),
                rs.getBoolean("assigned")),device.getDevice_id(),f1);
        //assigned
        if(!dev.isEmpty()){
            if(dev.get(0).getUsrid()==user_id){
                return "Already assigned";
            }
            else{
                return "Unassign it from User ID:"+dev.get(0).getUsrid();
            }
        }
        //not assigned
        else{
            try {
                jdbc.update(sql2, device.getDevice_id(), user_id,new Timestamp(System.currentTimeMillis()), f1);
                return "Successfully assigned";
            }
            catch(Exception e){
                jdbc.update(sql3,f1,new Timestamp(System.currentTimeMillis()),device.getDevice_id(),user_id);
                return "Successfully assigned";
            }
        }

    }

    @Override
    public String unAssign(int user_id, int device_id) {
        String upd = "UPDATE userdevice SET assigned=?, lastupdate=? WHERE usrid=? AND device_id=?";
        jdbc.update(upd, false, new Timestamp(System.currentTimeMillis()), user_id, device_id);
        return "Successfully unassigned";
    }

    @Override
    public HashMap<Integer, Integer> devicesPerUser(int idx) {
        String sqlA="SELECT * FROM userdevice";
        List<UserDeviceAssignment> devlist = jdbc.query(sqlA,(rs, rowNum)->new UserDeviceAssignment(rs.getInt("device_id"),
                rs.getInt("usrid"),rs.getTimestamp("lastupdate"),rs.getBoolean("assigned")));
        HashMap<Integer, Integer> hmap = new HashMap<Integer, Integer>();
        for (UserDeviceAssignment d: devlist) {
            if(d.getUsrid()==idx && d.isAssigned()) {
                if (hmap.containsKey(idx)) {
                    hmap.put(idx, hmap.get(idx) + 1);
                } else {
                    hmap.put(idx, 1);
                }
            }
        }
        if(hmap.isEmpty())
            hmap.put(idx,0);
        return hmap;
    }

    @Override
    public List<User> getDeviceHistory(int device_id) {
        String sql = "SELECT * FROM userdevice where device_id = ?";
        String sqla = "SELECT * FROM usr WHERE usrid=?";
        List<UserDeviceAssignment> devlist = jdbc.query(sql,(rs,row)->new UserDeviceAssignment(rs.getInt("device_id"),
                rs.getInt("usrid"),
                rs.getTimestamp("lastupdate"),
                rs.getBoolean("assigned")),device_id);

        List<User> usrlist = new ArrayList<>();
        for(UserDeviceAssignment dev:devlist){
            usrlist.add(jdbc.queryForObject(sqla,new Object[]{dev.getUsrid()},(rs,row)->new User(rs.getInt("usrid")
                    ,rs.getString("name"),
                    rs.getString("deptname"),
                    rs.getTimestamp("datetimez"), new UserObject(rs.getInt("usrid"),"***","***"))));
        }
        return usrlist;

    }

    @Override
    public HashMap<String, Integer> devicesPerDept(String dept_name) {
        String sqlA="SELECT * FROM usr";
        List<User> usrlist = jdbc.query(sqlA,(rs, rowNum)->new User(rs.getInt("usrid"),rs.getString("name"),
                rs.getString("deptname"),rs.getTimestamp("datetimez")));;
        HashMap<String, Integer> hmap = new HashMap<String, Integer>();
        for (User usr: usrlist) {
            if (usr.getDeptname().equalsIgnoreCase(dept_name)) {
                if (hmap.containsKey(dept_name)) {
                    if (!devicesPerUser(usr.getUsrid()).isEmpty()) {
                        hmap.put(dept_name, hmap.get(dept_name) + devicesPerUser(usr.getUsrid()).get(usr.getUsrid()));
                    }
                    else
                        continue;
                }
                else {
                    hmap.put(dept_name, devicesPerUser(usr.getUsrid()).get(usr.getUsrid()));
                }
            }
        }
        return hmap;
    }
}
