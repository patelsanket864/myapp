package com.employee.myapp.services;

import com.employee.myapp.models.Employee;
import com.employee.myapp.repositories.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepo employeeRepo;

    public Employee fetchEmployeeByUsername(String username) {
        return employeeRepo.getEmployeeByUsername(username);
    }

    public String addEmployee(Employee employee) {
        Employee a =employeeRepo.save(employee);
        return a.getId() >0 ? "yes":"no";
    }
}
