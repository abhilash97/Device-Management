package com.device.Dao;

import com.device.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * interface for database object access
 * */
@Repository
public interface UserRepository {

    List<User> getAllUsers();
    String updateUsername(int uid, String uname);
    String updateUserpass(int uid, String pass);
    String updateUser(User usr, int id);
    String deleteUser(int id);
    String insertUser(User u);
    HashMap<String,Integer> userPerDept(String dept);
    List<String> topN(String dept, int N);
}
