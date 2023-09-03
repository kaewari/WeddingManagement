package com.qltc.controller;

import com.qltc.pojo.Branch;
import com.qltc.pojo.Dish;
import com.qltc.pojo.Hall;
import com.qltc.service.BranchService;
import com.qltc.service.DishService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping //ok
    @ResponseStatus(HttpStatus.OK)
    public List<Branch> index(@RequestParam(name = "listHall", defaultValue = "false") boolean listHallYesOrNo,
            @RequestParam(name = "province", required = false) String province,
            @RequestParam(name = "district", required = false) String district,
            @RequestParam(name = "ward", required = false) String ward,
            @RequestParam(name = "quarter", required = false) String quarter,
            @RequestParam(name = "houseNumber", required = false) String houseNumber,
            @RequestParam(name = "inactive", required = false) Boolean inactive) {
        List<Branch> branches;
        if (province != null || district != null || ward != null || quarter != null
                || houseNumber != null || inactive != null) {
            branches = branchService.find(new HashMap<String, Object>() {
                {
                    put("province", province);
                    put("district", district);
                    put("ward", ward);
                    put("quarter", quarter);
                    put("houseNumber", houseNumber);
                    put("inactive", inactive);
                }
            });
        } else {
            branches = branchService.findAll();
        }

        if (branches != null) {
            branches.forEach((branch) -> {
                if (!listHallYesOrNo) {
                    branch.setHalls(null);
                } else {
                    branch.getHalls().forEach((hall) -> {
                        hall.setPrices(null);
                    });
                }
            });
        }
        return branches;
    }

    @GetMapping("/{branchId}") //ok
    public ResponseEntity<Branch> getBranchById(@PathVariable(name = "branchId", required = true) int branchId) {
        Branch branch = branchService.findById(branchId);
        return new ResponseEntity<>(branch, (branch != null ? HttpStatus.OK : HttpStatus.NOT_FOUND));
    }

    @PostMapping //ok
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Branch> addNewBranch(@RequestBody Branch branch) {
        if (branch != null && branchService.addOrUpdateBranch(branch)) {
            return new ResponseEntity<>(branch, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{branchId}") //ok
    public ResponseEntity<Branch> updateExistingBranch(@PathVariable("branchId") int id,
            @RequestBody Branch branch) {
        if (id == branch.getId()) {
            branchService.addOrUpdateBranch(branch);
            return new ResponseEntity<>(branch, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //error modifiedId not be null
    @PostMapping("/{branchId}/add-new-halls")
    public ResponseEntity<Branch> addNewHallToBranch(@PathVariable("branchId") int id,
            @RequestBody List<Hall> halls) {
        Branch branch = branchService.findById(id);
        if (branch != null && halls != null && !halls.isEmpty()) {
            halls.forEach(hall -> {
                branch.addHall(hall);
            });
            branchService.addOrUpdateBranch(branch);
            return new ResponseEntity<>(branch, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{branchId}/hall") //ok
    public ResponseEntity<List<Hall>> findAllHallInBranch(@PathVariable("branchId") int id) {
        Branch existing = branchService.findById(id);
        if (existing != null) {
            List<Hall> halls = new ArrayList<>(existing.getHalls());
            return new ResponseEntity<>(halls, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{branchId}/dish") //ok
    public ResponseEntity<List<Dish>> findAllDishInBranch(@PathVariable("branchId") int id) {
        Branch existing = branchService.findById(id);
        if (existing != null) {
            List dishes = dishService.findAllDishesInBranchId(id);
            return new ResponseEntity<>(dishes, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{branchId}/make-dish-unavailable") //ok
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

    @PostMapping("/{branchId}/make-dish-available") //ok
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

    @PostMapping("/{branchId}/add-dish") //ok
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

    @PostMapping("/{branchId}/remove-dish") //ok?
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

    @PutMapping("/{branchId}/deactivate") //ok
    public ResponseEntity deactivateBranch(@PathVariable("branchId") int id) {
        if (branchService.deactivateBranchById(id)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{branchId}/activate") //ok
    public ResponseEntity activateBranch(@PathVariable("branchId") int id) {
        if (branchService.activateBranchById(id)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{branchId}") //ok
    //500 error cannot delete by foreign key
    //if no any foreign key will delete ok
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
