package com.device.Dao;

import com.device.model.Department;

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
