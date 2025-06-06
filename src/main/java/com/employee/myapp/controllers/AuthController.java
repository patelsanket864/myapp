package com.employee.myapp.controllers;

import com.employee.myapp.models.Authenticate;
import com.employee.myapp.models.Employee;
import com.employee.myapp.services.CustomUserDetailService;
import com.employee.myapp.services.EmployeeService;
import com.employee.myapp.utilities.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CustomUserDetailService customUserDetailService;

    @PostMapping("/login") // Full path: /api/auth/login
    public ResponseEntity<Map<String, String>> authenticateAndGetToken(@RequestBody Authenticate authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            UserDetails userDetails = customUserDetailService.loadUserByUsername(authRequest.getUsername());
            String token = jwtUtility.generateJWT(userDetails.getUsername());

            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid username or password"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An unexpected error occurred: " + e.getMessage()));
        }
    }

    @PostMapping("/register") // Full path: /api/auth/register
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody Employee employee) {
        String message = employeeService.addEmployee(employee);
        if (message.equals("yes")) {
            UserDetails userDetails = customUserDetailService.loadUserByUsername(employee.getUsername());
            String token = jwtUtility.generateJWT(userDetails.getUsername());
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("message", "User registered successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", message));
        }
    }
}
