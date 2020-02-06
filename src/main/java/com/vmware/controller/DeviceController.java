package com.vmware.controller;


import com.vmware.Services.DeviceService;
import com.vmware.model.Department;
import com.vmware.model.Devices;
import com.vmware.model.User;
import com.vmware.model.UserDeviceAssignment;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/device")
@Api("DEVICE APIs")
public class DeviceController {

    @Autowired
    private DeviceService dev;

    @PostMapping("/insertdevice")
    @ApiOperation(value = "INSERT DEVICE", responseContainer = "List", response = Devices.class)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Success"),@ApiResponse(code=404,message = "Failed")})
    public String insertDevices(@RequestBody Devices device){
        return dev.insertDevice(device);
    }

    @PutMapping("/updatedevices")
    @ApiOperation(value = "UPDATE DEVICE", responseContainer = "List", response = Devices.class)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Success"),@ApiResponse(code=404,message = "Failed")})
    public List<Devices> updateDev(@RequestParam int idx, @RequestBody Devices device){
        return dev.updateDevice(idx,device);
    }

    @GetMapping("/getdevices")
    @ApiOperation(value = "GET DEVICE", responseContainer = "List", response = Devices.class)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Success"),@ApiResponse(code=404,message = "Failed")})
    public List<Devices> getDevices(){
        return dev.getDevices();
    }

    @GetMapping("/deviceperuser")
    @ApiOperation(value = "DEVICE PER USER", responseContainer = "List", response = Devices.class)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Success"),@ApiResponse(code=404,message = "Failed")})
    public HashMap<Integer,Integer> devicePerUser(@RequestParam int idx){
        return dev.devicesPerUser(idx);
    }

    @GetMapping("/deviceperdept")
    @ApiOperation(value = "DEVICE PER DEPT", responseContainer = "List", response = Devices.class)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Success"),@ApiResponse(code=404,message = "Failed")})
    public HashMap<String,Integer> devicePerDept(@RequestBody Department dept){
        return dev.devicesPerDept(dept.getDeptname());
    }

    @GetMapping("/devicehistory")
    @ApiOperation(value = "DEVICE HISTORY", responseContainer = "List", response = Devices.class)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Success"),@ApiResponse(code=404,message = "Failed")})
    public List<User> deviceHistory(@RequestParam int devid){
        return dev.getDeviceHistory(devid);
    }

    @PutMapping("/assigndevice")
    @ApiOperation(value = "ASSIGN DEVICE", responseContainer = "List", response = Devices.class)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Success"),@ApiResponse(code=404,message = "Failed")})
    public String assignDevice(@RequestParam int usrid, @RequestBody Devices devid){
        return dev.assign(usrid,devid);
    }

    @PutMapping("/unassigndevice")
    @ApiOperation(value = "UNASSIGN DEVICE", responseContainer = "List", response = Devices.class)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Success"),@ApiResponse(code=404,message = "Failed")})
    public String unAssignDevice(@RequestParam int usrid, @RequestParam int devid){
        return dev.unAssign(usrid,devid);
    }

    @GetMapping("/getdeviceassigns")
    @ApiOperation(value = "GET DEVICE ASSIGNS", responseContainer = "List", response = Devices.class)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Success"),@ApiResponse(code=404,message = "Failed")})
    public List<UserDeviceAssignment> deviceAssigns(){
        return dev.getDeviceAssignemnt();
    }
}
