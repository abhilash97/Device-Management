package com.vmware.Dao;

import com.vmware.model.Department;
import com.vmware.model.Devices;
import com.vmware.model.User;
import com.vmware.model.UserDeviceAssignment;

import java.util.HashMap;
import java.util.List;

/**
 * interface for database object access for devices db table
 * */

public interface DepartmentRepository {

    List<Department> getDept();
    String insertDept(Department dept);
    String updateDept(Department dept);
    String deleteDept(String deptname);
}
