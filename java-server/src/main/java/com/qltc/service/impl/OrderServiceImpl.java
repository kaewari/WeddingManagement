package com.qltc.service.impl;

import com.qltc.pojo.Order;
import com.qltc.pojo.OrderDetailsDish;
import com.qltc.pojo.OrderDetailsHall;
import com.qltc.pojo.OrderDetailsService;
import com.qltc.pojo.Wedding;
import com.qltc.repository.HallPriceRepository;
import com.qltc.repository.OrderRepository;
import com.qltc.repository.WeddingRepository;
import com.qltc.repository.WeddingServicePriceRepository;
import com.qltc.repository.WeddingServiceRepository;
import com.qltc.service.OrderService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private OrderRepository orderRepo;
    
    @Autowired
    private WeddingRepository weddingRepo;
    
    @Autowired
    private WeddingServiceRepository weddingServiceRepo;
    
    @Autowired
    private WeddingServicePriceRepository weddingServicePriceRepo;
    
    @Autowired
    private HallPriceRepository hallPriceRepo;
    
    @Override
    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    @Override
    public Order findById(int id) {
        return orderRepo.findById(id);
    }

    @Override
    public List<Order> find(Map<String, Object> findArgs) {
        return orderRepo.find(findArgs);
    }

    @Override
    public boolean addOrUpdate(Order oder) {
        return orderRepo.addOrUpdateOrder(oder);
    }

    @Override
    public boolean deleteById(int id) {
        return orderRepo.deleteOrderById(id);
    }

    @Override
    public boolean delete(Order oder) {
        return orderRepo.deleteOrder(oder);
    }

    @Override
    public boolean addWeddingToOrder(Order order, Wedding wedding) {
        return orderRepo.addWeddingToOrder(order, wedding);
    }

    @Override
    public boolean removeWeddingFromOrder(Order order, Wedding wedding) {
        if (wedding.equals(order.getWedding())) {
            return weddingRepo.delete(wedding);
        } else {
            return false;
        }
    }

    @Override
    public boolean addOrderDetailsDish(Order order, List<OrderDetailsDish> orderDetailsDishes) {
        return orderRepo.addDishToOrder(order, orderDetailsDishes);
    }

    @Override
    public boolean removeOrderDetailsDish(Order order, List<OrderDetailsDish> orderDetailsDishes) {
        return orderRepo.removeDishFromOrder(order, orderDetailsDishes);
    }

    @Override
    public boolean addOrderDetailsService(Order order, List<OrderDetailsService> orderDetailsServices) {
        return orderRepo.addServicePriceToOrder(order, orderDetailsServices);
    }

    @Override
    public boolean removeOrderDetailsService(Order order, List<OrderDetailsService> orderDetailsServices) {
            return orderRepo.removeServicePriceFromOrder(order, orderDetailsServices);
    }

    @Override
    public boolean addOrderDetailsHall(Order order, List<OrderDetailsHall> orderDetailsHalls) {
        return orderRepo.addHallPriceToOrder(order, orderDetailsHalls);
    }

    @Override
    public boolean removeOrderDetailsHall(Order order, List<OrderDetailsHall> orderDetailsHalls) {
        return orderRepo.removeHallPriceFromOrder(order, orderDetailsHalls);
    }

    @Override
    public OrderDetailsDish getOrderDetailsDishById(int id) {
        return orderRepo.getOrderDetailsDishById(id);
    }

    @Override
    public boolean updateOrderDetailsDish(OrderDetailsDish orderDetailsDish) {
        return orderRepo.addOrUpdateOrderDish(orderDetailsDish);
    }

    @Override
    public boolean deleteOrderDetailsDishById(int id) {
        return orderRepo.deleteOrderDishById(id);
    }

    @Override
    public boolean deleteOrderDetailsDish(OrderDetailsDish orderDetailsDish) {
        return orderRepo.deleteOrderDish(orderDetailsDish);
    }

    @Override
    public OrderDetailsService getOrderDetailsServiceById(int id) {
        return orderRepo.getOrderDetailsServiceById(id);
    }

    @Override
    public boolean updateOrderDetailsService(OrderDetailsService orderDetailsService) {
        return orderRepo.addOrUpdateOrderService(orderDetailsService);
    }

    @Override
    public boolean deleteOrderDetailsServiceById(int id) {
        return orderRepo.deleteOrderServiceById(id);
    }

    @Override
    public boolean deleteOrderDetailsService(OrderDetailsService orderDetailsService) {
        return orderRepo.deleteOrderService(orderDetailsService);
    }

    @Override
    public OrderDetailsHall getOrderDetailsHallById(int id) {
        return orderRepo.getOrderDetailsHallById(id);
    }

    @Override
    public boolean updateOrderDetailsHall(OrderDetailsHall orderDetailsHall) {
        return orderRepo.addOrUpdateOrderHallPrice(orderDetailsHall);
    }

    @Override
    public boolean deleteOrderDetailsHallById(int id) {
        return orderRepo.deleteOrderHallPriceById(id);
    }

    @Override
    public boolean deleteOrderDetailsHall(OrderDetailsHall orderDetailsHall) {
        return orderRepo.deleteOrderHallPrice(orderDetailsHall);
    }

    @Override
    public double calculateTotalOfOrder(Order order) {
        if (order == null) return 0;
        return orderRepo.calculateTotal(order.getId());
    }

     @Override	
    public double calculateTotalOfOrderById(int orderId) {	
        Order order = getOrderById(orderId);	
        if (order == null) {	
            return 0;	
        }	
        return orderRepo.calculateTotal(orderId);	
    }	
    
    //son
    
    @Override	
    public List<Order> getOrdersByEmployeeId(int employeeId) {	
        return this.orderRepo.getOrdersByEmployeeId(employeeId);	
    }	
    
    @Override	
    public List<Order> getOrdersByCustomerId(int customerId) {	
        return this.orderRepo.getOrdersByCustomerId(customerId);	
    }	
    @Override	
    public boolean getOrderByReceiptNumber(String receiptNumber) {	
        return this.orderRepo.getOrderByReceiptNumber(receiptNumber);	
    }	
    
    @Override	
    public boolean deleteOrderById(int orderId) {	
        return this.orderRepo.deleteOrderById(orderId);	
    }	
    
    @Override	
    public boolean deleteOrdersByCustomerId(int customerId) {	
        return this.orderRepo.deleteOrderById(customerId);	
    }
       @Override	
    public List<Order> getOrders() {	
        return orderRepo.getOrders();	
    }	
    @Override	
    public Order getOrderById(int id) {	
        return orderRepo.getOrderById(id);	
    }	
    @Override	
    public List<Order> searchOrders(Map<String, Object> findArgs) {	
        return orderRepo.searchOrders(findArgs);	
    }	
    @Override	
    public boolean addOrUpdateOrder(Order oder) {	
        return orderRepo.addOrUpdateOrder(oder);	
    }
}
