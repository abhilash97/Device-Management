package com.vmware.controller;

import com.vmware.Services.DepartmentService;
import com.vmware.model.Department;
import com.vmware.model.Devices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dept")
@Api("DEPARTMENT APIs")
public class DepartmentController {

    @Autowired
    private DepartmentService dep;

    @PostMapping("/insertdept")
    @ApiOperation(value = "INSERT DEPT", responseContainer = "List", response = Devices.class)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Success"),@ApiResponse(code=404,message = "Failed")})
    public String insertDepartment(@RequestBody Department dept){
        return dep.insertDept(dept);
    }

    @PutMapping("/updatedept")
    @ApiOperation(value = "UPDATE DEPT", responseContainer = "List", response = Devices.class)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Success"),@ApiResponse(code=404,message = "Failed")})
    public String updateDepartment(@RequestBody Department dept){
        return dep.updateDept(dept);
    }

    @GetMapping("/getdept")
    @ApiOperation(value = "GET DEPT", responseContainer = "List", response = Devices.class)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Success"),@ApiResponse(code=404,message = "Failed")})
    public List<Department> getDepartment(){
        return dep.getDept();
    }

    @PostMapping("/deletedept")
    @ApiOperation(value = "DELETE DEPT", responseContainer = "List", response = Devices.class)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Success"),@ApiResponse(code=404,message = "Failed")})
    public String deleteDepartment(@RequestBody Department d){
        return dep.deleteDept(d.getDeptname());
    }
}
