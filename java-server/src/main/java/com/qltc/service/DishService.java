package com.qltc.service;

import com.qltc.pojo.Branch;
import com.qltc.pojo.Dish;
import java.util.List;
import java.util.Map;

public interface DishService {

    public List<Dish> findAll();

    public List<Dish> findAllDishesInBranchId(int id);

    public Dish findById(int id);

    public List<Dish> find(Map<String, Object> findArgs);

    public List<Dish> findAllDishesRestaurantServes();

    public boolean addOrUpdateDish(Dish dish);

    public boolean deactivateDishById(int id);

    public boolean deactivateDish(Dish dish);

    public boolean activateDishById(int id);

    public boolean activateDish(Dish dish);

    public boolean deleteDishById(int id);

    public boolean deleteDish(Dish dish);

    public boolean addDishToBranch(Branch branch, Dish dish);

    public boolean removeDishFromBranch(Dish dish, Branch branch);

    public boolean unServeDishInBranch(Dish dish, Branch branch);

    public boolean reServeDishInBranch(Dish dish, Branch branch);
}
