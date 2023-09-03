package com.qltc.controller;

import com.qltc.pojo.Hall;
import com.qltc.pojo.HallPrice;
import com.qltc.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hall")
public class ApiHallController {

    @Autowired
    private HallService hallService;

    @GetMapping("/{hallId}") //ok
    public ResponseEntity<Hall> getById(@PathVariable("hallId") int id) {
        Hall existing = hallService.findById(id);
        if (existing != null) {
            return new ResponseEntity<>(existing, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{hallId}") //ok
    public ResponseEntity<Hall> updateExistingHall(@PathVariable("hallId") int id,
            @RequestBody Hall hall) {
        Hall existing = hallService.findById(id);
        if (!hall.getName().isEmpty()) {
            existing.setName(hall.getName());
        }
        if (!hall.getDescription().isEmpty()) {
            existing.setDescription(hall.getDescription());
        }
        if (!hall.getGuestUpTo().isEmpty()) {
            existing.setGuestUpTo(hall.getGuestUpTo());
        }
        if (!hall.getTableCount().isEmpty()) {
            existing.setTableCount(hall.getTableCount());
        }
        if (hall.getIsActive() != null) {
            existing.setIsActive(hall.getIsActive());
        }

        if (hall.getId() == id && hallService.updateExistingHall(existing)) {
            return new ResponseEntity<>(existing, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{hallId}/add-hall-price") //ok
    public ResponseEntity<Hall> addNewHallPrice(@PathVariable("hallId") int id,
            @RequestBody HallPrice hallPrice) {
        Hall hall = hallService.findById(id);
        if (hall != null) {
            hall.addHallPrice(hallPrice);
            if (hallService.updateExistingHall(hall)) {
                return new ResponseEntity<>(hall, HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update-hall-price/{hallPriceId}") //ok
    public ResponseEntity<HallPrice> updateExistingHallPrice(@PathVariable("hallPriceId") int id,
            @RequestBody HallPrice hallPrice) {
        HallPrice existing = hallService.findHallPriceById(id);
        if (!hallPrice.getPeriod().isEmpty()) {
            existing.setPeriod(hallPrice.getPeriod());
        }
        existing.setPrice(hallPrice.getPrice());

        if (hallPrice.getId() == id && hallService.addOrUpdateHallPrice(existing)) {
            return new ResponseEntity<>(hallPrice, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete-hall-price/{hallPriceId}") //ok
    public ResponseEntity deleteHallPrice(@PathVariable("hallPriceId") int id) {
        HallPrice existing = hallService.findHallPriceById(id);
        if (existing != null && hallService.removePrice(existing)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{hallId}/deactivate") //ok
    public ResponseEntity deactivateExistingHall(@PathVariable("hallId") int id) {
        Hall existing = hallService.findById(id);
        if (existing != null && hallService.deactivateHall(existing)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{hallId}/activate") //ok
    public ResponseEntity activateExistingHall(@PathVariable("hallId") int id) {
        Hall existing = hallService.findById(id);
        if (existing != null && hallService.activateHall(existing)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{hallId}") //ok
    public ResponseEntity delete(@PathVariable("hallId") int id) {
        Hall existing = hallService.findById(id);
        if (existing != null && hallService.deleteHall(existing)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
