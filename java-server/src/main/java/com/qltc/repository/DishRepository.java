package com.qltc.repository;

import com.qltc.pojo.Branch;
import com.qltc.pojo.Dish;
import java.util.List;
import java.util.Map;

public interface DishRepository {

    public List<Dish> findAll();

    public Dish findById(int id);

    public List<Dish> find(Map<String, Object> findArgs);

    public List<Dish> findAllInBranchId(int branchId);

    public boolean addOrUpdateDish(Dish dish);

    public boolean deactivateDishById(int id);

    public boolean deactivateDish(Dish dish);

    public boolean deleteDishById(int id);

    public boolean deleteDish(Dish dish);

    public boolean addDishToBranch(Branch branch, Dish dish);

    public boolean removeDishFromBranch(Dish dish, Branch branch);

    public boolean setAvailableForDishInBranch(Dish dish, Branch branch, boolean isAvailable);
}
