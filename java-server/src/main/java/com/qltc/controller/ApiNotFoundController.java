/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sonho
 */
@RestController
@RequestMapping({"/api/", "/"})
public class ApiNotFoundController {

    @RequestMapping(value = "**")
    public ResponseEntity<String> getWrongRequest() {
        return new ResponseEntity<>("Wrong URL", HttpStatus.NOT_FOUND);
    }
}
