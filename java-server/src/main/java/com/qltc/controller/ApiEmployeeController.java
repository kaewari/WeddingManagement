package com.qltc.controllers;

import com.qltc.pojo.Employee;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee")
public class ApiEmployeeController {
    
    @GetMapping
    public List<?> getAllEmployee() {
        return new ArrayList<>();
    }
    
    @GetMapping("/{employeeId}")
    public Employee getById(@PathVariable("employeeId") int id) {
        return null;
    }
}
