package com.qltc.repository;

import com.qltc.pojo.Order;
import java.util.List;
import java.util.Map;

public interface OrderRepository {

    public List<Order> getOrders();

    public Order getOrderById(int id);

    List<Order> getOrdersByEmployeeId(int employeeId);

    List<Order> getOrdersByCustomerId(int customerId);

    public boolean getOrderByReceiptNumber(String receiptNumber);

    public List<Order> searchOrders(Map<String, Object> findArgs);

    public boolean addOrUpdateOrder(Order order);

    public boolean deleteOrderById(int id);

    public boolean deleteOrdersByCustomerId(int customerId);

    //count, stats
}
