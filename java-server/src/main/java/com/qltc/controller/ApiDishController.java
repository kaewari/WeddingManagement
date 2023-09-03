package com.qltc.controllers;

import com.qltc.pojo.Branch;
import com.qltc.pojo.Dish;
import com.qltc.services.BranchService;
import com.qltc.services.DishService;
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
@RequestMapping("/api/dish")
public class ApiDishController {
    
    @Autowired
    private DishService dishService;
    
    @Autowired
    private BranchService branchService;
    
    @GetMapping //ok
    @ResponseStatus(HttpStatus.OK)
    public List<Dish> getAll(@RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "weddingOnly", required = false) Boolean wedding,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex) {
        return dishService.find(new HashMap<String, Object>() {{
                put("keyword", keyword);
                put("weddingOnly", wedding);
                put("pageSize", pageSize);
                put("pageIndex", pageIndex);
        }});
    }
    
    
    @GetMapping("/restaurants") //ok
    @ResponseStatus(HttpStatus.OK)
    public List<Dish> getAllRestaurantDishes() {
        return dishService.findAllDishesRestaurantServes();
    }
    
    @GetMapping("/{dishId:[0-9]+}") //ok
    public ResponseEntity<Dish> getById(@PathVariable("dishId") int id) {
        Dish existing = dishService.findById(id);
        if (existing != null) {
            return new ResponseEntity<>(existing, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping //ok
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Dish> addNewDish(@RequestBody Dish dish) {
        //upload image
        if (dishService.addOrUpdateDish(dish)) {
            return new ResponseEntity(dish,HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/{dishId}") //ok
    public ResponseEntity updateExistingDish(@PathVariable("dishId") int id, @RequestBody Dish dish) {
        //upload image
        if (dish.getId() == id && dishService.addOrUpdateDish(dish)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/{dishId}/remove-dish-from-branch")  //ok
    public ResponseEntity removeDishFromBranch(@PathVariable("dishId") int id, 
            @RequestParam("branchId") int branchId) {
        Dish dish = dishService.findById(id);
        Branch branch = branchService.findById(branchId);
        if (dishService.removeDishFromBranch(dish, branch)) {
             return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    
    //add dish from branch
    
    @PostMapping("/{dishId}/deactivate") //ok
    public ResponseEntity deactivateDish(@PathVariable("dishId") int id) {
        if (dishService.deactivateDishById(id)) {
           return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/{dishId}/activate") //ok
    public ResponseEntity activateDish(@PathVariable("dishId") int id) {
        if (dishService.activateDishById(id)) {
           return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{dishId}") //ok
    public ResponseEntity deleteDish(@PathVariable("dishId") int id) {
        if (dishService.deleteDishById(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    
}
