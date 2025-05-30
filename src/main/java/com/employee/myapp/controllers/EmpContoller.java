package com.employee.myapp.controllers;

import com.employee.myapp.models.Employee;
import com.employee.myapp.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmpContoller {

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/api/employees")
    public ResponseEntity<String> addEmployee(@RequestBody Employee employee) {
        String emp = employeeService.addEmployee(employee);
        return ResponseEntity.ok(emp);
    }

    @GetMapping("/api/employees/{username}")
    public ResponseEntity<Object> getEmployee(@PathVariable("username") String username, @RequestParam(value = "dept", required = false) String departname){
        System.out.println("departname:"+departname);
        Employee emp=employeeService.fetchEmployeeByUsername(username);
        if(emp==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee details not found");
        return ResponseEntity.ok(emp);
    }
}
