/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.qltc.repository;

import com.qltc.pojo.OrderDetailsService;
import java.util.List;

/**
 *
 * @author sonho
 */
public interface OrderDetailsServiceRepository {

    OrderDetailsService getOrderDetailsServiceById(int id);

    List<OrderDetailsService> getOrderDetailsServiceByOrderId(int orderId);

    List<OrderDetailsService> getOrderDetailsServiceByServiceId(int serviceId);

    List<OrderDetailsService> getOrderDetailServices();

    OrderDetailsService addOrderDetailServiceById(OrderDetailsService orderDetailsService);

    boolean updateOrderDetailServiceById(OrderDetailsService orderDetailsService);

    boolean deleteOrderDetailServiceById(OrderDetailsService orderDetailsService);
}
