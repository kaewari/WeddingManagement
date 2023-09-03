package com.qltc.repositories;

import com.qltc.pojo.WeddingServicePrice;
import java.util.List;

public interface WeddingServicePriceRepository {
    public List<WeddingServicePrice> findAll();
    public WeddingServicePrice findById(int id);
    public boolean addOrUpdate(WeddingServicePrice price);
    public boolean deactivateOrActivateById(int id, boolean isAvailable);
    public boolean deactivateOrActivate(WeddingServicePrice price, boolean isAvailable);
    public boolean deleteById(int id);
    public boolean delete(WeddingServicePrice price);
}
