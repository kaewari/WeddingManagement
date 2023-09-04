package com.qltc.repositories;

import com.qltc.pojo.Employee;
import java.util.List;
import java.util.Map;

public interface EmployeeRepository {
    public List<Employee> findAll(Map<String, Object> findArgs);
    public Employee findById(int id);
    public boolean addOrUpdate(Employee employee);
    public boolean deleteById(int id);
    public boolean delete(Employee employee);
}
