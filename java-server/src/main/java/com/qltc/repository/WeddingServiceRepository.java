package com.qltc.repository;

import com.qltc.pojo.WeddingService;
import com.qltc.pojo.WeddingServicePrice;
import java.util.List;

public interface WeddingServiceRepository {

    public List<WeddingService> findAll();

    public WeddingService findById(int id);

    public boolean addOrUpdate(WeddingService service);

    public boolean addPriceToService(WeddingService service, List<WeddingServicePrice> prices);

    public boolean deleteById(int id);

    public boolean delete(WeddingService service);
}
