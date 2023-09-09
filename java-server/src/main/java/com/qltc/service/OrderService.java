package com.qltc.service;

import com.qltc.pojo.Order;
import com.qltc.pojo.OrderDetailsDish;
import com.qltc.pojo.OrderDetailsHall;
import com.qltc.pojo.OrderDetailsService;
import com.qltc.pojo.Wedding;
import java.util.List;
import java.util.Map;

public interface OrderService {
    public List<Order> findAll();
    public Order findById(int id);
    public List<Order> find(Map<String, Object> findArgs);
    public boolean addOrUpdate(Order oder);
    public boolean deleteById(int id);
    public boolean delete(Order oder);
    // Wedding
    public boolean addWeddingToOrder(Order order, Wedding wedding);
    public boolean removeWeddingFromOrder(Order order, Wedding wedding);
    //Order Details Dish
    public boolean addOrderDetailsDish(Order order, List<OrderDetailsDish> orderDetailsDishs);
    public boolean removeOrderDetailsDish(Order order, List<OrderDetailsDish> orderDetailsDishs);
    //Order Details Service
    public boolean addOrderDetailsService(Order order, List<OrderDetailsService> orderDetailsServices);
    public boolean removeOrderDetailsService(Order order, List<OrderDetailsService> orderDetailsServices);
    //Order Details Hall
    public boolean addOrderDetailsHall(Order order, List<OrderDetailsHall> orderDetailsHalls);
    public boolean removeOrderDetailsHall(Order order, List<OrderDetailsHall> orderDetailsHalls);
    
    //Basic action of Order Details Dish
    public OrderDetailsDish getOrderDetailsDishById(int id);
    public boolean updateOrderDetailsDish(OrderDetailsDish orderDetailsDish);
    public boolean deleteOrderDetailsDishById(int id);
    public boolean deleteOrderDetailsDish(OrderDetailsDish orderDetailsDish);
    
    //Basic action of Order Details Service (<T> - Service Price)
    public OrderDetailsService getOrderDetailsServiceById(int id);
    public boolean updateOrderDetailsService(OrderDetailsService orderDetailsService);
    public boolean deleteOrderDetailsServiceById(int id);
    public boolean deleteOrderDetailsService(OrderDetailsService orderDetailsService);
    
    //Basic action of  Order Details Hall (<T> - Hall Price)
    public OrderDetailsHall getOrderDetailsHallById(int id);
    public boolean updateOrderDetailsHall(OrderDetailsHall orderDetailsHall);
    public boolean deleteOrderDetailsHallById(int id);
    public boolean deleteOrderDetailsHall(OrderDetailsHall orderDetailsHall);
    
    public double calculateTotalOfOrder(Order order);
    public double calculateTotalOfOrderById(int orderId);
}
