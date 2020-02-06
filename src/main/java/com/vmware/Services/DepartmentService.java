package com.vmware.Services;

import com.vmware.Dao.DepartmentRepository;
import com.vmware.model.Department;
import com.vmware.model.UserDeviceAssignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentService implements DepartmentRepository {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public List<Department> getDept() {
        String sql = "SELECT * FROM dept";
        return jdbc.query(sql,(rs, rowNum)->new Department(rs.getString("depttype"), rs.getString("deptname")));
    }

    @Override
    public String insertDept(Department dept) {
        String sql = "INSERT INTO dept(depttype, deptname) VALUES(?,?)";
        jdbc.update(sql,dept.getDepttype(),dept.getDeptname());
        return "Successfully inserted";
    }

    @Override
    public String updateDept(Department dept) {
        String sql = "UPDATE dept SET depttype=? WHERE deptname=?";
        jdbc.update(sql,dept.getDepttype(),dept.getDeptname());
        return "Successfully updated";
    }

    @Override
    public String deleteDept(String deptname) {
        String sql = "DELETE FROM dept WHERE deptname=?";
        jdbc.update(sql,deptname);
        return "Successfully deleted";
    }
}
