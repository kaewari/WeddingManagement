package com.qltc.service.impl;

import com.qltc.pojo.Branch;
import com.qltc.pojo.Dish;
import com.qltc.repository.DishRepository;
import com.qltc.service.DishService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishRepository dishRepo;

    @Override
    public List<Dish> findAll() {
        return dishRepo.findAll();
    }

    @Override
    public List<Dish> findAllDishesInBranchId(int branchId) {
        return dishRepo.findAllInBranchId(branchId);
    }

    @Override
    public Dish findById(int id) {
        return dishRepo.findById(id);
    }

    @Override
    public List<Dish> find(Map<String, Object> findArgs) {
        return dishRepo.find(findArgs);
    }

    @Override
    public List<Dish> findAllDishesRestaurantServes() {
        return dishRepo.find(new HashMap<String, Object>() {
            {
                put("weddingOnly", false);
            }
        }
        );
    }

    @Override
    public boolean addOrUpdateDish(Dish dish) {
        return dishRepo.addOrUpdateDish(dish);
    }

    @Override
    public boolean deactivateDishById(int id) {
        Dish dish = dishRepo.findById(id);
        dish.setIsAvailable(false);
        return dishRepo.addOrUpdateDish(dish);
    }

    @Override
    public boolean deactivateDish(Dish dish) {
        dish.setIsAvailable(false);
        return dishRepo.addOrUpdateDish(dish);
    }

    @Override
    public boolean activateDishById(int id) {
        Dish dish = dishRepo.findById(id);
        dish.setIsAvailable(true);
        return dishRepo.addOrUpdateDish(dish);
    }

    @Override
    public boolean activateDish(Dish dish) {
        dish.setIsAvailable(true);
        return dishRepo.addOrUpdateDish(dish);
    }

    @Override
    public boolean deleteDishById(int id) {
        return dishRepo.deleteDishById(id);
    }

    @Override
    public boolean deleteDish(Dish dish) {
        return dishRepo.deleteDish(dish);
    }

    @Override
    public boolean addDishToBranch(Branch branch, Dish dish) {
        return dishRepo.addDishToBranch(branch, dish);
    }

    @Override
    public boolean removeDishFromBranch(Dish dish, Branch branch) {
        return dishRepo.removeDishFromBranch(dish, branch);
    }

    @Override
    public boolean unServeDishInBranch(Dish dish, Branch branch) {
        return dishRepo.setAvailableForDishInBranch(dish, branch, false);
    }

    @Override
    public boolean reServeDishInBranch(Dish dish, Branch branch) {
        return dishRepo.setAvailableForDishInBranch(dish, branch, true);
    }
}
