/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.service.impl;

import com.qltc.pojo.Branch;
import com.qltc.pojo.Employee;
import com.qltc.repository.EmployeeRepository;
import com.qltc.service.EmployeeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author sonho
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepo;

    @Override
    public Employee getEmployeeById(int id) {
        return this.employeeRepo.getEmployeeById(id);
    }

    @Override
    public List<Employee> getEmployees() {
        return this.employeeRepo.getEmployees();
    }

    @Override
    public Employee addEmployee(Employee e) {
        return this.employeeRepo.addEmployee(e);
    }

    @Override
    public boolean deleteEmployee(int id) {
        return this.employeeRepo.deleteEmployee(id);
    }

    @Override
    public boolean updateEmployee(Employee e) {
        return this.employeeRepo.updateEmployee(e);
    }

    @Override
    public Employee getEmployeeByUserId(int userId) {
        return this.employeeRepo.getEmployeeByUserId(userId);
    }

    @Override
    public Employee getEmployeeByIdentityNumber(String identityNumber) {
        return this.employeeRepo.getEmployeeByIdentityNumber(identityNumber);
    }

    @Override
    public Branch getEmployeeByBranchId(int id) {
        return this.employeeRepo.getEmployeeByBranchId(id);
    }
}
