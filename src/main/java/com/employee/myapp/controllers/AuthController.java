package com.employee.myapp.controllers;

import com.employee.myapp.models.Authenticate;
import com.employee.myapp.utilities.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtility jwtUtility;

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> generateToken(@RequestBody Authenticate authRequest) {
        authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
         String token =jwtUtility.generateJWT(authRequest.getUsername());

        Map<String, String> response = new HashMap<>();
        response.put("token", token); // <-- This is correct
        return ResponseEntity.ok(response);
    }
}
