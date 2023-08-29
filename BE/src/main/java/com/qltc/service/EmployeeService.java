/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.qltc.service;

import com.qltc.pojo.Employee;
import java.util.List;

/**
 *
 * @author sonho
 */
public interface EmployeeService {

    Employee getEmployeeById(int id);

    List<Employee> getEmployees();

    boolean addOrUpdateEmployee(Employee e);

    boolean deleteEmployee(int id);
}
