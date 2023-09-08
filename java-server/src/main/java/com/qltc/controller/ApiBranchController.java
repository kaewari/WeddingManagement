package com.qltc.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.qltc.json.JsonMarkup;
import com.qltc.pojo.Branch;
import com.qltc.pojo.Dish;
import com.qltc.pojo.Hall;
import com.qltc.pojo.User;
import com.qltc.service.BranchService;
import com.qltc.service.DishService;
import com.qltc.service.UserService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/branch")
public class ApiBranchController {

    @Autowired
    private BranchService branchService;

    @Autowired
    private DishService dishService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    @JsonView(JsonMarkup.CoreData.class)
    @ResponseStatus(HttpStatus.OK)
    public List<Branch> index(@RequestParam(name = "listHall", defaultValue = "false") boolean listHallYesOrNo,
            @RequestParam(name = "province", required = false) String province,
            @RequestParam(name = "district", required = false) String district,
            @RequestParam(name = "ward", required = false) String ward,
            @RequestParam(name = "quarter", required = false) String quarter,
            @RequestParam(name = "houseNumber", required = false) String houseNumber,
            @RequestParam(name = "inactive", defaultValue = "false") Boolean inactive) {
        
        return branchService.find(new HashMap<String, Object>() {
            {
                put("province", province);
                put("district", district);
                put("ward", ward);
                put("quarter", quarter);
                put("houseNumber", houseNumber);
                put("inactive", inactive);
            }
        });
}

    @GetMapping("/{branchId}")
    public ResponseEntity<Branch> getBranchById(@PathVariable(name = "branchId", required = true) int branchId) {
        Branch branch = branchService.findById(branchId);
        return new ResponseEntity<>(branch, (branch != null ? HttpStatus.OK : HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasAuthority('ADD_NEW_BRANCH')")
    @PostMapping(
        consumes =  MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JsonMarkup.FullData.class)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Branch> addNewBranch(@RequestBody @JsonView(JsonMarkup.CoreData.class) Branch branch) {
        if (branch != null && branchService.addOrUpdateBranch(branch)) {
            return new ResponseEntity<>(branch, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PreAuthorize("hasAuthority('MODIFY_EXISTING_BRANCH')")
    @PutMapping(path = "/{branchId}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(JsonMarkup.CoreData.class)
    public ResponseEntity<Branch> updateExistingBranch(@PathVariable("branchId") int id,
            @RequestBody @JsonView(JsonMarkup.CoreData.class) Branch branch) {
        if (id == branch.getId()) {
            branchService.addOrUpdateBranch(branch);
            return new ResponseEntity<>(branch, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADD_NEW_HALL')")
    @PostMapping(path = "/{branchId}/add-new-halls",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Branch> addNewHallToBranch(@PathVariable("branchId") int id,
            @RequestBody List<Hall> halls, Principal principal) {
        Branch branch = branchService.findById(id);
        User currentUser = userService.getUserByName(principal.getName());
        if (branch != null && halls != null && !halls.isEmpty()) {
            for (Hall hall : halls) {
                if (hall.getName() == null || hall.getDescription() == null
                        || hall.getGuestUpTo() == null || hall.getTableCount() == null) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
                hall.setUser(currentUser);
                branch.addHall(hall);
            }
            branchService.addOrUpdateBranch(branch);
            return new ResponseEntity<>(branch, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{branchId}/hall")
    public ResponseEntity<List<Hall>> findAllHallInBranch(@PathVariable("branchId") int id) {
        Branch existing = branchService.findById(id);
        if (existing != null) {
            List<Hall> halls = new ArrayList<>(existing.getHalls());
            return new ResponseEntity<>(halls, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{branchId}/dish")
    @JsonView(JsonMarkup.CoreData.class)
    public ResponseEntity<List<Dish>> findAllDishInBranch(@PathVariable("branchId") int id) {
        Branch existing = branchService.findById(id);
        if (existing != null) {
            List dishes = dishService.findAllDishesInBranchId(id);
            return new ResponseEntity<>(dishes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_BRANCH')")
    @PostMapping("/{branchId}/make-dish-unavailable")
    public ResponseEntity deactivateDishOfBranch(@PathVariable("branchId") int id,
            @RequestParam("dishId") int dishId) {
        Branch branch = branchService.findById(id);
        Dish dish = dishService.findById(dishId);
        if (dishService.unServeDishInBranch(dish, branch)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_BRANCH')")
    @PostMapping("/{branchId}/make-dish-available")
    public ResponseEntity activateDishOfBranch(@PathVariable("branchId") int id,
            @RequestParam("dishId") int dishId) {
        Branch branch = branchService.findById(id);
        Dish dish = dishService.findById(dishId);
        if (dishService.reServeDishInBranch(dish, branch)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_BRANCH')")
    @PostMapping("/{branchId}/add-dish")
    public ResponseEntity addDishToBranch(@PathVariable("branchId") int id,
            @RequestParam("dishId") int dishId) {
        Branch branch = branchService.findById(id);
        Dish dish = dishService.findById(dishId);
        if (dishService.addDishToBranch(branch, dish)) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_BRANCH')")
    @PostMapping("/{branchId}/remove-dish")
    public ResponseEntity removeDishFromBranch(@PathVariable("branchId") int id,
            @RequestParam("dishId") int dishId) {
        Branch branch = branchService.findById(id);
        Dish dish = dishService.findById(dishId);
        if (dishService.removeDishFromBranch(dish, branch)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_BRANCH')")
    @PutMapping("/{branchId}/deactivate")
    public ResponseEntity deactivateBranch(@PathVariable("branchId") int id) {
        if (branchService.deactivateBranchById(id)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_BRANCH')")
    @PutMapping("/{branchId}/activate")
    public ResponseEntity activateBranch(@PathVariable("branchId") int id) {
        if (branchService.activateBranchById(id)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    
    @PreAuthorize("hasAuthority('DELETE_EXISTING_BRANCH')")
    //only works with first time created and as soon be delete
    //do not use
    @DeleteMapping("/{branchId}") //stuck with foreign key parent of many rows in other table
    public ResponseEntity deleteBranch(@PathVariable(name = "branchId") int branchId) {
        Branch branch = branchService.findById(branchId);
        if (branch != null) {
            branchService.deleteBranch(branch);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
