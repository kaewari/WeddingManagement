/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.qltc.repository;

import java.util.List;
import java.util.Map;

/**
 *
 * @author sonho
 */
public interface RevenueRepository {

    List<Object[]> getTopBestSellerDishes(int limit);

    List<Object[]> getTopBestSellerServices(int limit);

    List<Object[]> getTopBestSellerHalls(int limit);

    List<Object[]> getStatsRevenueByYear(Map<String, String> params);

    List<Object[]> getStatsRevenueByDateRange(Map<String, String> params);
}
