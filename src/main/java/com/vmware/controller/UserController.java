package com.vmware.controller;

import com.vmware.Services.UserService;
import com.vmware.model.Department;
import com.vmware.model.User;
import com.vmware.model.UserObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
@Api("USER APIS")
@ComponentScan
public class UserController {

    @Autowired
    private UserService usv;

    @GetMapping("/allusers")
    @ApiOperation(value = "getAllUsers",responseContainer = "List",response = User.class)
    @ApiResponses(value={@ApiResponse(code=200,message = "Success"),
    @ApiResponse(code=404,message="Failed")})
    public List<User> getUsers(){
        return usv.getAllUsers();
    }

    @PutMapping("/updateuser")
    @ApiOperation(value = "UPDATE USER",responseContainer = "List",response = User.class)
    @ApiResponses(value={@ApiResponse(code=200,message = "Success"),
            @ApiResponse(code=404,message="Failed")})
    public String updateuser(@RequestBody User usr,@RequestParam int idx){
        return usv.updateUser(usr,idx);
    }

    @PutMapping("/updateusername")
    @ApiOperation(value = "UPDATE USERNAME",responseContainer = "List",response = User.class)
    @ApiResponses(value={@ApiResponse(code=200,message = "Success"),
            @ApiResponse(code=404,message="Failed")})
    public String updateusername(@RequestBody UserObject usr){
        return usv.updateUsername(usr.getUsrid(),usr.getUsername());
    }

    @PutMapping("/updatepassword")
    @ApiOperation(value = "UPDATE PASSWORD",responseContainer = "List",response = User.class)
    @ApiResponses(value={@ApiResponse(code=200,message = "Success"),
            @ApiResponse(code=404,message="Failed")})
    public String updatepassword(@RequestBody UserObject usr){
        return usv.updateUsername(usr.getUsrid(),usr.getPassword());
    }

    @DeleteMapping("/deleteuser")
    @ApiOperation(value = "DELETE USER",responseContainer = "List",response = User.class)
    @ApiResponses(value={@ApiResponse(code=200,message = "Success"),
            @ApiResponse(code=404,message="Failed")})
    public String deleteUser(@RequestParam int idx){
        return usv.deleteUser(idx);
    }

    @PostMapping("/insertuser")
    @ApiOperation(value = "INSERT USER",responseContainer = "List",response = User.class)
    @ApiResponses(value={@ApiResponse(code=200,message = "Success"),
            @ApiResponse(code=404,message="Failed")})
    public String insertUser(@RequestBody User usr){
        return usv.insertUser(usr);
    }

    @GetMapping("/userperdept")
    @ApiOperation(value = "getUsersPerDepartment",responseContainer = "List",response = User.class)
    @ApiResponses(value={@ApiResponse(code=200,message = "Success"),
            @ApiResponse(code=404,message="Failed")})
    public HashMap<String,Integer> getUserPerDept(@RequestBody Department dept){
        return usv.userPerDept(dept.getDeptname());
    }

    @PostMapping("/topnusersperdept")
    @ApiOperation(value = "top N users",responseContainer = "List",response = User.class)
    @ApiResponses(value={@ApiResponse(code=200,message = "Success"),
            @ApiResponse(code=404,message="Failed")})
    public List<String> topnUsers(@RequestBody Department dept, @RequestParam int N){
        return usv.topN(dept.getDeptname(),N);
    }

}
