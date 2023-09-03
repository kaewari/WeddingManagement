package com.qltc.repositories;

import com.qltc.pojo.Order;
import java.util.List;
import java.util.Map;


public interface OrderRepository {
    public List<Order> findAll();
    public Order findById(long id);
    public List<Order> find(Map<String, Object> findArgs);
    public boolean addOrUpdateOrder(Order order);
    public boolean deleteOrderById(int id);
    public boolean deleteOrder(Order order);
    //count, stats
}
