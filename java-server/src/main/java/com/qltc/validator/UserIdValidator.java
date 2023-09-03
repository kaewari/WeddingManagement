/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.validator;

import com.qltc.pojo.Employee;
import com.qltc.service.EmployeeService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author sonho
 */
@Component
public class UserIdValidator implements Validator {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Employee.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Map<String, String> e = (Map<String, String>) target;
        if (this.employeeService.getEmployeeById(Integer.parseInt(e.get("userId"))) != null) {
            errors.rejectValue("userId", "employee.userId.existMsg");
        }
    }
}
