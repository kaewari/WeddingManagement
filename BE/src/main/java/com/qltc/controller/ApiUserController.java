/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.controller;

import com.qltc.components.JwtService;
import com.qltc.pojo.User;
import com.qltc.service.UserService;
import java.security.Principal;
import java.util.Map;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author sonho
 */
@RestController
@RequestMapping("/api")
public class ApiUserController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;

    @PostMapping(path = "/login/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<String> login(@RequestBody User user) {
        if (!this.userService.authUser(user.getName(), user.getPassword())) {
            return new ResponseEntity<>("Wrong name or password", HttpStatus.BAD_REQUEST);
        } else {
            String token = this.jwtService.generateTokenLogin(user.getName());
            JSONObject resToken = new JSONObject();
            resToken.put("access_token", token);
            return new ResponseEntity<>(resToken.toString(), HttpStatus.OK);
        }
    }

    @PostMapping("/test/")
    @CrossOrigin
    public ResponseEntity<String> test(Principal pricipal) {
        return new ResponseEntity<>(pricipal.getName(), HttpStatus.OK);
    }

    @PostMapping(path = "/users/",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<User> addUser(@RequestParam Map<String, String> params, @RequestPart MultipartFile avatar) {
        User user = this.userService.addUser(params, avatar);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping(path = "/current-user/", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<User> details(Principal pricipal) {
        User u = this.userService.getUserByName(pricipal.getName());
        return new ResponseEntity<>(u, HttpStatus.OK);
    }
}
