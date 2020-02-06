package com.device.controller;


import com.device.Services.DeviceService;
import com.device.model.Department;
import com.device.model.Devices;
import com.device.model.User;
import com.device.model.UserDeviceAssignment;
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

    /** create a new device*/
    @PostMapping("/insertdevice")
    @ApiOperation(value = "INSERT DEVICE", responseContainer = "List", response = Devices.class)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Success"),@ApiResponse(code=404,message = "Failed")})
    public String insertDevices(@RequestBody Devices device){
        return dev.insertDevice(device);
    }

    /** update a device*/
    @PutMapping("/updatedevices")
    @ApiOperation(value = "UPDATE DEVICE", responseContainer = "List", response = Devices.class)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Success"),@ApiResponse(code=404,message = "Failed")})
    public List<Devices> updateDev(@RequestParam int idx, @RequestBody Devices device){
        return dev.updateDevice(idx,device);
    }

    /** display all devices*/
    @GetMapping("/getdevices")
    @ApiOperation(value = "GET DEVICE", responseContainer = "List", response = Devices.class)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Success"),@ApiResponse(code=404,message = "Failed")})
    public List<Devices> getDevices(){
        return dev.getDevices();
    }

    /** display no of devices assigned to a user*/
    @GetMapping("/deviceperuser")
    @ApiOperation(value = "DEVICE PER USER", responseContainer = "List", response = Devices.class)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Success"),@ApiResponse(code=404,message = "Failed")})
    public HashMap<Integer,Integer> devicePerUser(@RequestParam int idx){
        return dev.devicesPerUser(idx);
    }

    /** display total no. of devices assigned to users of a department*/
    @GetMapping("/deviceperdept")
    @ApiOperation(value = "DEVICE PER DEPT", responseContainer = "List", response = Devices.class)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Success"),@ApiResponse(code=404,message = "Failed")})
    public HashMap<String,Integer> devicePerDept(@RequestBody Department dept){
        return dev.devicesPerDept(dept.getDeptname());
    }

    /** display a device's assignment history-list of user(s) it was/is assigned*/
    @GetMapping("/devicehistory")
    @ApiOperation(value = "DEVICE HISTORY", responseContainer = "List", response = Devices.class)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Success"),@ApiResponse(code=404,message = "Failed")})
    public List<User> deviceHistory(@RequestParam int devid){
        return dev.getDeviceHistory(devid);
    }

    /** assign a device to a user*/
    @PutMapping("/assigndevice")
    @ApiOperation(value = "ASSIGN DEVICE", responseContainer = "List", response = Devices.class)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Success"),@ApiResponse(code=404,message = "Failed")})
    public String assignDevice(@RequestParam int usrid, @RequestBody Devices devid){
        return dev.assign(usrid,devid);
    }

    /** unassign device from a user*/
    @PutMapping("/unassigndevice")
    @ApiOperation(value = "UNASSIGN DEVICE", responseContainer = "List", response = Devices.class)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Success"),@ApiResponse(code=404,message = "Failed")})
    public String unAssignDevice(@RequestParam int usrid, @RequestParam int devid){
        return dev.unAssign(usrid,devid);
    }

    /** display all the user-device assignments - which device is assigned to which user*/
    @GetMapping("/getdeviceassigns")
    @ApiOperation(value = "GET DEVICE ASSIGNS", responseContainer = "List", response = Devices.class)
    @ApiResponses(value = {@ApiResponse(code = 200,message = "Success"),@ApiResponse(code=404,message = "Failed")})
    public List<UserDeviceAssignment> deviceAssigns(){
        return dev.getDeviceAssignemnt();
    }
}
