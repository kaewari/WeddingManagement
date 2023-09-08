package com.qltc.controller;

import com.qltc.pojo.Order;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alo")
public class newController {
    
    
    @PostMapping
    public ResponseEntity get123(@RequestBody Order order) {
        return new ResponseEntity(order.getOrderDetailsServices(), HttpStatus.OK);
    }
}
