package com.qltc.controllers;

import com.qltc.pojo.Permission;
import com.qltc.services.PermissionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/permission")
public class ApiPermissionController {
    
    @Autowired
    private PermissionService permissionService;
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Permission> getAll() {
        return permissionService.findAll();
    }
    
    @GetMapping("/{value:[a-zA-Z0-9-_]+}")
    public List<Permission> getByType(@PathVariable("value") String type) {
        return permissionService.findByName(type);
    }
}
