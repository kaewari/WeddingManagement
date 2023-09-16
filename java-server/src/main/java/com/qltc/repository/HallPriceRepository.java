package com.qltc.repository;

import com.qltc.pojo.HallPrice;
import java.util.List;

public interface HallPriceRepository {

    public List<HallPrice> findAll();

    public HallPrice findById(int id);

    public HallPrice findByPeriod(String period);

    public List<HallPrice> findByPrice(double fromPrice, double toPrice);

    public boolean addOrUpdate(HallPrice hallPrice);

    public boolean deleteById(int id);

    public boolean delete(HallPrice hallPrice);
}
