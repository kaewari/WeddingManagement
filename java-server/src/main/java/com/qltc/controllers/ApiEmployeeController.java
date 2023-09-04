package com.qltc.controllers;

import com.qltc.pojo.Employee;
import com.qltc.services.EmployeeService;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employee")
public class ApiEmployeeController {
    
    @Autowired
    private EmployeeService employeeService;
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<?> getAllEmployee(@RequestParam(name = "branchId", required = false) Integer branchId
            , @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex
            , @RequestParam(name = "pageSize", defaultValue = "10") int pageSize
            , @RequestParam(name = "keyword") String keyword) {
        return employeeService.findAll(new HashMap<String, Object>() {{
            put("branchId", branchId);
            put("keyword", keyword);
            put("pageIndex", pageIndex);
            put("pageSize", pageSize);
        }});
    }
    
    @GetMapping("/{employeeId}")
    public ResponseEntity<Employee> getById(@PathVariable("employeeId") int id) {
        Employee existing = employeeService.findById(id);
        if (existing != null) {
            return new ResponseEntity<>(existing, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PutMapping("/{employeeId}")
    public ResponseEntity updateExistingEmployee(@PathVariable("employeeId") int id,
                @RequestBody Employee employee) {
        Employee existing = employeeService.findById(id);
        if (!employee.getFirstName().isEmpty()) existing.setFirstName(employee.getFirstName());
        if (!employee.getLastName().isEmpty()) existing.setLastName(employee.getLastName());
        if (!employee.getPosition().isEmpty()) existing.setPosition(employee.getPosition());
        if (!employee.getIdentityNumber().isEmpty()) existing.setIdentityNumber(employee.getIdentityNumber());
        
        if (existing != null && employeeService.addOrUpdate(employee)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{employeeId}")
    public ResponseEntity deleteExistingEmployee(@PathVariable("employeeId") int id) {
        if (employeeService.deleteById(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
