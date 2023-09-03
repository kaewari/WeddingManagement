package com.qltc.services;

import com.qltc.pojo.Hall;
import com.qltc.pojo.HallPrice;
import java.util.List;
import java.util.Map;

public interface HallService {
    public List<Hall> findAll();
    public Hall findById(int id);
    public List<Hall> find(Map<String, Object> findArgs);
    public boolean createNewHall(Hall hall);
    public boolean updateExistingHall(Hall hall);
    public HallPrice findHallPriceById(int id);
    public boolean setPriceToHall(Hall hall, String period, double price);
    public boolean setPriceToHall(Hall hall, HallPrice price);
    public boolean removePrice(HallPrice price);
    public boolean addOrUpdateHallPrice(HallPrice hallPrice);
    public boolean removePriceFromHall(String period);
    public boolean deactivateHallById(int id);
    public boolean deactivateHall(Hall hall);
    public boolean activateHallById(int id);
    public boolean activateHall(Hall hall);
    public boolean deleteHallById(int id);
    public boolean deleteHall(Hall hall);
}
