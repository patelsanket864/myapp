package com.employee.myapp.repositories;

import com.employee.myapp.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepo extends JpaRepository<Employee,Integer> {
    @Query(value = "SELECT * FROM employee WHERE username = ?1", nativeQuery = true)
    Employee getEmployeeByUsername(String username);
}
