/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.controller;

import com.qltc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sonho
 */
@RestController
@RequestMapping("/api")
public class ApiAdminController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/admin/id/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> getUserPermissionById(@PathVariable int id) {
        return new ResponseEntity<>(this.userService.getUserById(id), HttpStatus.OK);
    }
}
