package com.employee.myapp.services;

import com.employee.myapp.models.Employee;
import com.employee.myapp.repositories.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.init-admin", havingValue = "true")
public class DatabaseInitializer implements CommandLineRunner {
    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if the admin user already exists
        if (employeeRepo.getEmployeeByUsername("admin") == null) {
            Employee adminUser = new Employee();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("password")); // Encode the password
            adminUser.setFirstName("Admin");
            adminUser.setLastName("User");
            adminUser.setEmail("admin@example.com");
            employeeRepo.save(adminUser);
        }
    }
}
