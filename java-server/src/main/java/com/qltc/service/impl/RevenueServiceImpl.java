/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.service.impl;

import com.qltc.repository.RevenueRepository;
import com.qltc.service.RevenueService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author sonho
 */
@Service
public class RevenueServiceImpl implements RevenueService {

    @Autowired
    private RevenueRepository revenueRepository;

    @Override
    public List<Object[]> getStatsRevenue(Map<String, String> params) {
        return this.revenueRepository.getStatsRevenue(params);
    }

    @Override
    public List<Object[]> getTopBestSellerDishes(int limit) {
        return this.revenueRepository.getTopBestSellerDishes(limit);
    }

    @Override
    public List<Object[]> getTopBestSellerServices(int limit) {
        return this.revenueRepository.getTopBestSellerServices(limit);
    }

    @Override
    public List<Object[]> getTopBestSellerHalls(int limit) {
        return this.revenueRepository.getTopBestSellerHalls(limit);
    }

}
