package com.qltc.service.impl;

import com.qltc.pojo.Order;
import com.qltc.repository.OrderRepository;
import com.qltc.service.OrderService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author sonho
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getOrders() {
        return this.orderRepository.getOrders();
    }

    @Override
    public List<Order> getOrdersByEmployeeId(int employeeId) {
        return this.orderRepository.getOrdersByEmployeeId(employeeId);
    }

    @Override
    public List<Order> getOrdersByCustomerId(int customerId) {
        return this.orderRepository.getOrdersByCustomerId(customerId);
    }

    @Override
    public List<Order> searchOrders(Map<String, Object> findArgs) {
        return this.orderRepository.searchOrders(findArgs);
    }

    @Override
    public boolean addOrUpdateOrder(Order order) {
        return this.orderRepository.addOrUpdateOrder(order);
    }

    @Override
    public boolean deleteOrderById(int orderId) {
        return this.orderRepository.deleteOrderById(orderId);
    }

    @Override
    public boolean deleteOrdersByCustomerId(int customerId) {
        return this.orderRepository.deleteOrdersByCustomerId(customerId);
    }

    @Override
    public boolean getOrderByReceiptNumber(String receiptNumber) {
        return this.orderRepository.getOrderByReceiptNumber(receiptNumber);
    }

    @Override
    public Order getOrderById(int id) {
        return this.orderRepository.getOrderById(id);
    }
}
