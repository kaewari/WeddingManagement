/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.controller;

import com.qltc.pojo.Employee;
import com.qltc.service.EmployeeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sonho
 */
@RestController
@RequestMapping("/api")
public class ApiEmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(path = "/employees/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<List<Employee>> list() {
        return new ResponseEntity<>(this.employeeService.getEmployees(), HttpStatus.OK);
    }

    @RequestMapping(path = "/employees/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.employeeService.getEmployeeById(id), HttpStatus.OK);
    }
}
