/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.qltc.service;

import com.qltc.pojo.Branch;
import com.qltc.pojo.Employee;
import java.util.List;

/**
 *
 * @author sonho
 * @param <T>
 * @param <V>
 */
public interface EmployeeService {

    Employee getEmployeeByUserId(int userId);

    Employee getEmployeeByIdentityNumber(String identityNumber);

    Employee getEmployeeById(int id);

    List<Employee> getEmployees();

    Employee addEmployee(Employee e);

    boolean updateEmployee(Employee e);

    boolean deleteEmployee(int id);

    Branch getEmployeeByBranchId(int id);
}
