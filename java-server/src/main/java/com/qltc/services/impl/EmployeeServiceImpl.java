package com.qltc.services.impl;

import com.qltc.pojo.Employee;
import com.qltc.repositories.EmployeeRepository;
import com.qltc.services.EmployeeService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepo;

    @Override
    public List<Employee> findAll(Map<String, Object> findArgs) {
        return employeeRepo.findAll(findArgs);
    }

    @Override
    public Employee findById(int id) {
        return employeeRepo.findById(id);
    }

    @Override
    public boolean addOrUpdate(Employee employee) {
        return employeeRepo.addOrUpdate(employee);
    }

    @Override
    public boolean deleteById(int id) {
        return employeeRepo.deleteById(id);
    }

    @Override
    public boolean delete(Employee employee) {
        return employeeRepo.delete(employee);
    }
    
}
