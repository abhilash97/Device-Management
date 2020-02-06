package com.device.Services;

import com.device.Dao.UserRepository;
import com.device.model.User;
import com.device.model.UserObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;

@Component
public class UserService implements UserRepository {


    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private DeviceService dsv;

    public UserObject autoTemplate(int id){
        return new UserObject(id,"***","***");
    }

    @Override
    public List<User> getAllUsers(){
        String sql="SELECT * FROM usr";

        return jdbc.query(sql,(rs, rowNum)->new User(rs.getInt("usrid"),rs.getString("name"),
                    rs.getString("deptname"),rs.getTimestamp("datetimez"),
                autoTemplate(rs.getInt("usrid"))));
    }

    @Override
    public String updateUsername(int uid, String uname) {
        String ss = "UPDATE usercreds SET username=? WHERE usrid=?";
        jdbc.update(ss,uname,uid);
        return "Successfully updated";
    }

    @Override
    public String updateUserpass(int uid, String pass) {
        String ss = "UPDATE usercreds SET password=? WHERE usrid=?";
        jdbc.update(ss,pass,uid);
        return "Successfully updated";
    }

    @Override
    public String updateUser(User usr, int id) {
        String sql = "UPDATE usr SET usrid = ?, name = ?, deptname = ?, datetimez = ? WHERE usrid=?";
        jdbc.update(sql,usr.getUsrid(),usr.getName(),usr.getDeptname(),new Timestamp(System.currentTimeMillis()),id);
        return "User updated";
    }

    @Override
    public String deleteUser(int id) {
        String sql = "DELETE FROM usr WHERE usr.usrid=?";
        jdbc.update(sql,id);
        return "User successfully deleted";
    }

    @Override
    public String insertUser(User u) {
        String sqlB = "INSERT INTO usercreds(usrid, username, password) VALUES(?,?,?)";
        String sql = "INSERT INTO usr(usrid, name, deptname, datetimez) VALUES(?,?,?,?)";
        jdbc.update(sql,u.getUsrid(),u.getName(),u.getDeptname(),new Timestamp(System.currentTimeMillis()));
        try{
            jdbc.update(sqlB,u.getUsrid(),u.getCredentials().getUsername(),u.getCredentials().getPassword());
        }
        catch(Exception e){
            return "User with same username already exists";
        }
        return "Successfully created user";
    }

    @Override
    public HashMap<String, Integer> userPerDept(String dept) {
        String sqlA="SELECT * FROM usr";
        List<User> usrlist = jdbc.query(sqlA,(rs, rowNum)->new User(rs.getInt("usrid"),rs.getString("name"),
                rs.getString("deptname"),rs.getTimestamp("datetimez")));
        HashMap<String, Integer> hmap = new HashMap<String, Integer>();
        for (User usr: usrlist) {
            if(usr.getDeptname().equalsIgnoreCase(dept)){
                if (hmap.containsKey(dept)) {
                    hmap.put(dept, hmap.get(dept) + 1);
                } else {
                    hmap.put(dept, 1);
                }
            }
        }
        return hmap;
    }


    @Override
    public List<String> topN(String dept, int N) {
        String sql="SELECT * FROM usr WHERE deptname=?";
        List<User> usrlist = jdbc.query(sql,(rs, rowNum)->new User(rs.getInt("usrid"),rs.getString("name"),
                rs.getString("deptname"),rs.getTimestamp("datetimez"), autoTemplate(rs.getInt("usrid"))),dept);
        HashMap<String,Integer> lmap = new HashMap<>();
        for (User usr: usrlist) {
            lmap.put("ID:"+usr.getUsrid()+" "+usr.getName(), dsv.devicesPerUser(usr.getUsrid()).get(usr.getUsrid()));
        }
        ArrayList<String> ls=new ArrayList<String>();
        lmap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> ls.add(x.getKey()+" "+x.getValue()));
        return ls.subList(0,N);
    }
}
