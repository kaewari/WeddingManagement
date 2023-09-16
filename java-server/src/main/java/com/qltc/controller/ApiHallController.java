package com.qltc.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.qltc.json.JsonMarkup;
import com.qltc.pojo.Hall;
import com.qltc.pojo.HallPrice;
import com.qltc.pojo.User;
import com.qltc.service.HallService;
import com.qltc.service.UserService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    
    @Autowired
    private UserService userService;

    @GetMapping("/{hallId}")
    public ResponseEntity<Hall> getById(@PathVariable("hallId") int id) {
        Hall existing = hallService.findById(id);
        if (existing != null) {
            return new ResponseEntity<>(existing, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_HALL')")
    @PutMapping("/{hallId}")
    public ResponseEntity<Hall> updateExistingHall(@PathVariable("hallId") int id,
            @RequestBody Hall hall, Principal principal) {
        Hall existing = hallService.findById(id);
        User currentUser = userService.getUserByName(principal.getName());
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
        
        existing.setUser(currentUser);

        if (hall.getId() == id && hallService.updateExistingHall(existing)) {
            return new ResponseEntity<>(existing, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADD_NEW_HALL_PRICE')")
    @PostMapping("/{hallId}/add-hall-price")
    public ResponseEntity<Hall> addNewHallPrice(@PathVariable("hallId") int id,
            @ModelAttribute HallPrice hallPrice) {
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

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_HALL_PRICE')")
    @PostMapping("/update-hall-price/{hallPriceId}")
    public ResponseEntity<HallPrice> updateExistingHallPrice(@PathVariable("hallPriceId") int id,
            @ModelAttribute HallPrice hallPrice) {
        HallPrice existing = hallService.findHallPriceById(id);
        if (hallPrice.getPeriod() != null && !hallPrice.getPeriod().isEmpty()) {
            existing.setPeriod(hallPrice.getPeriod());
        }
        if (!Double.isNaN(hallPrice.getPrice())) {
            existing.setPrice(hallPrice.getPrice());
        }
        if (!Float.isNaN(hallPrice.getDiscount())) {
            existing.setDiscount(hallPrice.getDiscount());
        }

        if (hallService.addOrUpdateHallPrice(existing)) {
            return new ResponseEntity<>(existing, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('DELETE_EXISTING_HALL_PRICE')")
    @DeleteMapping("/delete-hall-price/{hallPriceId}")
    public ResponseEntity deleteHallPrice(@PathVariable("hallPriceId") int id) {
        HallPrice existing = hallService.findHallPriceById(id);
        if (existing != null && hallService.removePrice(existing)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_HALL')")
    @PostMapping("/{hallId}/deactivate") 
    public ResponseEntity deactivateExistingHall(@PathVariable("hallId") int id) {
        Hall existing = hallService.findById(id);
        if (existing != null && hallService.deactivateHall(existing)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_HALL')")
    @PostMapping("/{hallId}/activate")
    public ResponseEntity activateExistingHall(@PathVariable("hallId") int id) {
        Hall existing = hallService.findById(id);
        if (existing != null && hallService.activateHall(existing)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('DELETE_EXISTING_HALL')")
    @DeleteMapping("/{hallId}")
    public ResponseEntity delete(@PathVariable("hallId") int id) {
        Hall existing = hallService.findById(id);
        if (existing != null && hallService.deleteHall(existing)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
