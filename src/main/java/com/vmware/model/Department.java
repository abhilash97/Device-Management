package com.vmware.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Department {
    private String deptname;
    private String depttype;

    public String getDeptname(){
        return deptname;
    }
    public String getDepttype(){
        return depttype;
    }
}

