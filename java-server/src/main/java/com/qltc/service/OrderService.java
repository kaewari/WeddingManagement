package com.qltc.service;

import com.qltc.pojo.Order;
import java.util.List;
import java.util.Map;

public interface OrderService {

    List<Order> getOrders();

    Order getOrderById(int id);

    List<Order> getOrdersByEmployeeId(int employeeId);

    List<Order> getOrdersByCustomerId(int customerId);

    boolean getOrderByReceiptNumber(String receiptNumber);

    List<Order> searchOrders(Map<String, Object> findArgs);

    boolean addOrUpdateOrder(Order order);

    boolean deleteOrderById(int orderId);

    boolean deleteOrdersByCustomerId(int customerId);
}
