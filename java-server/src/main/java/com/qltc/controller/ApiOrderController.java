/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author dev
 */
@RestController
@RequestMapping("/api")
public class ApiOrderController {

    @GetMapping(path = "/orders/")
    @CrossOrigin
    public ResponseEntity<String> getOrders() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
