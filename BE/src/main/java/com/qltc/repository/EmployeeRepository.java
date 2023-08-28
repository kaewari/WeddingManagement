/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.qltc.repository;

import com.qltc.pojo.Employee;
import java.util.List;

/**
 *
 * @author sonho
 */
public interface EmployeeRepository {

    Employee getEmployeeById(Integer id);

    List<Employee> getEmployees();

    boolean addOrUpdateEmployee(Employee e);

    boolean deleteEmployee(Integer id);
}
