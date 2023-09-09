/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.controller;

import com.qltc.pojo.Employee;
import com.qltc.pojo.User;
import com.qltc.pojo.UserGroup;
import com.qltc.pojo.UserGroupPermission;
import com.qltc.pojo.UserPermission;
import com.qltc.service.BranchService;
import com.qltc.service.EmployeeService;
import com.qltc.service.PermissionService;
import com.qltc.service.UserGroupService;
import com.qltc.service.UserPermissionService;
import com.qltc.service.UserService;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.cloudinary.json.JSONException;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author sonho
 */
@RestController
@RequestMapping("/api")
public class ApiEmployeeController {

    @Autowired
    private JSONObject message;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private UserService userService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private UserPermissionService userPermissionService;
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private PermissionService permissionService;

    @RequestMapping(path = "/employees/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<List<Employee>> list() {
        return new ResponseEntity<>(this.employeeService.getEmployees(), HttpStatus.OK);
    }

    @GetMapping(path = "/employees/id/{id}/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/employees/identity/{identity-number}/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<Employee> getEmployeeByIdentityNumber(@PathVariable(value = "identity-number") String identityNumber) {
        return new ResponseEntity<>(this.employeeService.getEmployeeByIdentityNumber(identityNumber), HttpStatus.OK);
    }

    @PostMapping(path = "/employees/add/",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<Object> addEmployee(@RequestBody Map<String, String> e, @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            if (this.userService.findUserInfo("phone", e.get("phone"))) {
                message.put("Msg", "This phone existed");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
            }
            if (this.employeeService.getEmployeeByIdentityNumber(e.get("identityNumber")) != null) {
                message.put("Msg", "This identity number existed");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
            }
            User user = new User();
            user.setName(e.get("name"));
            user.setAddress(e.get("address"));
            user.setEmail(e.get("email"));
            user.setPhone(e.get("phone"));
            user.setIdentityNumber(e.get("identityNumber"));
            user.setIsActive(Boolean.FALSE);
            user.setPassword(e.get("password"));
            user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            User isUser = this.userService.addUser(user, file);
            if (isUser == null) {
                message.put("Msg", "This user does not exists");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
            }
            if (this.employeeService.getEmployeeByUserId(isUser.getId()) != null) {
                message.put("Msg", "This user id existed");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
            }
            Employee employee = new Employee();
            employee.setFirstName(e.get("firstName"));
            employee.setLastName(e.get("lastName"));
            employee.setIdentityNumber(e.get("identityNumber"));
            employee.setPosition(e.get("position"));
            employee.setBranchId(this.branchService.findById(Integer.parseInt((String) e.get("branchId"))));
            employee.setUserId(this.userService.getUserById(isUser.getId()));
            Employee isEmployee = this.employeeService.addEmployee(employee);
            if (isEmployee != null) {
                UserGroup ug = this.userGroupService.findByName("WAITER");
                if (this.userGroupService.addUserToGroup(this.userService.getUserById(isUser.getId()), ug)) {
                    List<UserGroupPermission> userGroupPermissions = this.userGroupService.findByGroupId(ug.getId());
                    userGroupPermissions.forEach(ugp -> {
                        UserPermission userPermission = new UserPermission();
                        userPermission.setUser(isUser);
                        userPermission.setPermission(this.permissionService.findById(ugp.getPermission().getId()));
                        userPermission.setAllow(Boolean.TRUE);
                        this.userPermissionService.addUserPermission(userPermission);
                    });
                }
                return new ResponseEntity<>(isEmployee, HttpStatus.CREATED);
            } else {
                message.put("Msg", "Create employee failure");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
            }
        } catch (NumberFormatException | JSONException ex) {
            message.put("Msg", "Cannot create employee");
            return new ResponseEntity<>(ex.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/employees/update/{id}/",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<Object> updateEmployee(@PathVariable(value = "id") int id,
            @RequestBody Map<String, String> e) {
        try {
            Employee employee = this.employeeService.getEmployeeById(id);
            if (this.userService.getUserById(Integer.parseInt(e.get("userId"))) == null) {
                message.put("Msg", "This user does not exists");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
            }
            if (!Objects.equals(this.employeeService.getEmployeeByUserId(Integer.parseInt(e.get("userId"))).getId(), employee.getId())) {
                message.put("Msg", "This user id existed");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
            }
            if (!Objects.equals(this.employeeService.getEmployeeByIdentityNumber(e.get("identityNumber")).getId(), employee.getId())) {
                message.put("Msg", "This identity number existed");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
            }
            employee.setFirstName(e.get("firstName"));
            employee.setLastName(e.get("lastName"));
            employee.setIdentityNumber(e.get("identityNumber"));
            employee.setPosition(e.get("position"));
            employee.setBranchId(this.branchService.findById(Integer.parseInt((String) e.get("branchId"))));
            employee.setUserId(this.userService.getUserById(Integer.parseInt((String) e.get("userId"))));
            boolean isEmployee = this.employeeService.updateEmployee(employee);
            if (isEmployee) {
//                message.put("Msg", "Update employee successfully");
                return new ResponseEntity<>(this.employeeService.getEmployeeById(id), HttpStatus.OK);
            } else {
                message.put("Msg", "Update employee failure");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);

            }
        } catch (Exception ex) {
            message.put("Msg", "Update create employee");
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/employees/delete/{id}")
    @CrossOrigin
    public ResponseEntity<Object> deleteEmployee(@PathVariable(value = "id") int id) {
        return new ResponseEntity<>(this.employeeService.deleteEmployee(id), HttpStatus.OK);
    }
}
