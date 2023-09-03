package com.qltc.services.impl;

import com.qltc.pojo.Hall;
import com.qltc.pojo.HallPrice;
import com.qltc.repositories.HallPriceRepository;
import com.qltc.repositories.HallRepository;
import com.qltc.services.HallService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HallServiceImpl implements HallService {
    
    @Autowired
    private HallRepository hallRepo;
    
    @Autowired
    private HallPriceRepository hallPriceRepo;

    @Override
    public List<Hall> findAll() {
        return hallRepo.findAll();
    }

    @Override
    public Hall findById(int id) {
        return hallRepo.findById(id);
    }

    @Override
    public List<Hall> find(Map<String, Object> findArgs) {
        return hallRepo.find(findArgs);
    }

    @Override
    public boolean createNewHall(Hall hall) {
        return hallRepo.addOrUpdateHall(hall);
    }

    @Override
    public boolean updateExistingHall(Hall hall) {
        return hallRepo.addOrUpdateHall(hall);
    }
    
    @Override
    public HallPrice findHallPriceById(int id) {
        return hallPriceRepo.findById(id);
    }
    
    @Override
    public boolean setPriceToHall(Hall hall, String period, double price) {
        HallPrice newPrice = new HallPrice();
        newPrice.setPeriod(period);
        newPrice.setPrice(price);
        newPrice.setHall(hall);
        return hallPriceRepo.addOrUpdate(newPrice);
    }
    
    @Override
    public boolean setPriceToHall(Hall hall, HallPrice price) {
        price.setHall(hall);
        return hallPriceRepo.addOrUpdate(price);
    }
    
    @Override
    public boolean addOrUpdateHallPrice(HallPrice hallPrice) {
        return hallPriceRepo.addOrUpdate(hallPrice);
    }
    
    @Override
    public boolean removePrice(HallPrice price) {
        return hallPriceRepo.delete(price);
    }
            
    @Override        
    public boolean removePriceFromHall(String period){
        HallPrice hallPrice = hallPriceRepo.findByPeriod(period);
        if (hallPrice != null) {
            return removePrice(hallPrice);
        } else {
            return false;
        }
    }

    @Override
    public boolean deactivateHallById(int id) {
        return hallRepo.deactivateOrActivateHallById(id, false);
    }

    @Override
    public boolean deactivateHall(Hall hall) {
        return hallRepo.deactivateOrActivateHall(hall, false);
    }

    @Override
    public boolean activateHallById(int id) {
        return hallRepo.deactivateOrActivateHallById(id, true);
    }

    @Override
    public boolean activateHall(Hall hall) {
        return hallRepo.deactivateOrActivateHall(hall, true);
    }

    @Override
    public boolean deleteHallById(int id) {
        return hallRepo.deleteHallById(id);
    }

    @Override
    public boolean deleteHall(Hall hall) {
        return hallRepo.deleteHall(hall);
    }
    
}
