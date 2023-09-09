package com.qltc.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.annotation.JsonView;
import com.qltc.json.JsonMarkup;
import com.qltc.pojo.Branch;
import com.qltc.pojo.Dish;
import com.qltc.service.BranchService;
import com.qltc.service.DishService;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    
    @Autowired
    private Cloudinary cloudinary;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Dish> getAll(@RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "weddingOnly", required = false) Boolean wedding,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex) {
        return dishService.find(new HashMap<String, Object>() {
            {
                put("keyword", keyword);
                put("weddingOnly", wedding);
                put("pageSize", pageSize);
                put("pageIndex", pageIndex);
            }
        });
    }

    @GetMapping("/restaurants")
    @ResponseStatus(HttpStatus.OK)
    public List<Dish> getAllRestaurantDishes() {
        return dishService.findAllDishesRestaurantServes();
    }

    @GetMapping("/{dishId:[0-9]+}")
    public ResponseEntity<Dish> getById(@PathVariable("dishId") int id) {
        Dish existing = dishService.findById(id);
        if (existing != null) {
            return new ResponseEntity<>(existing, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ADD_NEW_DISH')")
    @PostMapping
    @Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Dish> addNewDish(@JsonView(JsonMarkup.CoreData.class) @ModelAttribute Dish dish) {
        //upload into cloudinary
        if (dish.getFile() != null) {
            try {
                Map<String, Object> result = cloudinary.uploader().upload(dish.getFile().getBytes(), 
                        ObjectUtils.emptyMap());
                dish.setImage((String) result.get("secure_url"));   
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }
        }
        if (dishService.addOrUpdateDish(dish)) {
            return new ResponseEntity(dish, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_DISH')")
    @PostMapping("/{dishId}/update")
    @Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity updateExistingDish(@PathVariable("dishId") int id, @ModelAttribute Dish dish) {
        Dish existing = dishService.findById(id);
        if (existing == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        
        if (dish.getName() != null && !dish.getName().isEmpty()) existing.setName(dish.getName());
        if (!Double.isNaN(dish.getPrice())) existing.setPrice(dish.getPrice());
        if (!Float.isNaN(dish.getDiscount())) existing.setDiscount(dish.getDiscount());
        if (dish.getType() != null && !dish.getType().isEmpty()) existing.setType(dish.getType());
        if (dish.getUnit() != null && !dish.getUnit().isEmpty()) existing.setUnit(dish.getUnit());
        if (dish.getWOnly() != null) existing.setWOnly(dish.getWOnly());
        if (dish.getIsAvailable() != null) existing.setIsAvailable(dish.getIsAvailable());
        
        //upload into cloudinary
        if (dish.getFile() != null) {
            try {
                Map<String, Object> result = cloudinary.uploader().upload(dish.getFile().getBytes(), 
                        ObjectUtils.emptyMap());
                existing.setImage((String) result.get("secure_url"));
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }
        }
        
        if (dishService.addOrUpdateDish(existing)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    
    //not use until now
    @PreAuthorize("hasAuthority('MODIFY_EXISTING_DISH')")
    @PostMapping("/{dishId}/remove-dish-from-branch")
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

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_DISH')")
    @PostMapping("/{dishId}/deactivate")
    public ResponseEntity deactivateDish(@PathVariable("dishId") int id) {
        if (dishService.deactivateDishById(id)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_DISH')")
    @PostMapping("/{dishId}/activate")
    public ResponseEntity activateDish(@PathVariable("dishId") int id) {
        if (dishService.activateDishById(id)) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{dishId}")
    //not use
    //or only when immediately created
    public ResponseEntity deleteDish(@PathVariable("dishId") int id) {
        if (dishService.deleteDishById(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
