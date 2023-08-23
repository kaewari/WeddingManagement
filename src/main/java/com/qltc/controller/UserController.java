/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.controller;

import com.qltc.pojo.UserAccount;
import com.qltc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sonho
 */
@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login/")
    @ResponseStatus(HttpStatus.OK)
    public UserAccount login(@RequestBody UserAccount userAccount) {
        return userAccount;
    }

    @GetMapping("/current-user/")
    @ResponseStatus(HttpStatus.OK)
    public UserDetails getCurrentUser(@RequestBody UserAccount userAccount) {
        return this.userService.loadUserByUsername(userAccount.getName());
    }
}
