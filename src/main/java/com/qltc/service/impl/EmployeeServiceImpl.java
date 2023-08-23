/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.service.impl;

import com.qltc.pojo.Employee;
import com.qltc.repository.EmployeeRepository;
import com.qltc.service.EmployeeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author sonho
 */
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepo;

    @Override
    public Employee getEmployeeById(Integer id) {
        return this.employeeRepo.getEmployeeById(id);
    }

    @Override
    public List<Employee> getEmployees() {
        return this.employeeRepo.getEmployees();
    }

    @Override
    public boolean addOrUpdateEmployee(Employee e) {
        return this.employeeRepo.addOrUpdateEmployee(e);
    }

    @Override
    public boolean deleteEmployee(Integer id) {
        return this.employeeRepo.deleteEmployee(id);
    }

}
