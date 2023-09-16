package com.qltc.repository;

import com.qltc.pojo.Dish;
import com.qltc.pojo.HallPrice;
import com.qltc.pojo.Order;
import com.qltc.pojo.OrderDetailsDish;
import com.qltc.pojo.OrderDetailsHall;
import com.qltc.pojo.OrderDetailsService;
import com.qltc.pojo.Wedding;
import com.qltc.pojo.WeddingService;
import java.util.List;
import java.util.Map;

public interface OrderRepository {

    public List<Order> findAll();

    public Order findById(int id);

    public List<Order> find(Map<String, Object> findArgs);

    public boolean addOrUpdateOrder(Order order);
    
    public boolean addWeddingToOrder(Order order, Wedding wedding);

    public boolean deleteOrderById(int id);

    public boolean deleteOrder(Order order);
    
    public boolean addDishToOrder(Order order, List<OrderDetailsDish> orderDetailsDishes);
    public boolean addServicePriceToOrder(Order order, List<OrderDetailsService> orderDetailsServices);
    public boolean addHallPriceToOrder(Order order, List<OrderDetailsHall> orderDetailsHalls);
    public boolean removeDishFromOrder(Order order, List<OrderDetailsDish> orderDetailsDishes);
    public boolean removeServicePriceFromOrder(Order order, List<OrderDetailsService> orderDetailsServices);
    public boolean removeHallPriceFromOrder(Order order, List<OrderDetailsHall> orderDetailsHalls);
    
    //order detail dish
    public OrderDetailsDish getOrderDetailsDishById(int id);
    public boolean addOrUpdateOrderDish(OrderDetailsDish orderDetailsDish);
    public boolean deleteOrderDishById(int id);
    public boolean deleteOrderDish(OrderDetailsDish orderDetailsDish);
    
    //order detail wedding service
    public OrderDetailsService getOrderDetailsServiceById(int id);
    public boolean addOrUpdateOrderService(OrderDetailsService orderDetailsService);
    public boolean deleteOrderServiceById(int id);
    public boolean deleteOrderService(OrderDetailsService orderDetailsService);
    
    //order detail hall price
    public OrderDetailsHall getOrderDetailsHallById(int id);
    public boolean addOrUpdateOrderHallPrice(OrderDetailsHall orderDetailsHall);
    public boolean deleteOrderHallPriceById(int id);
    public boolean deleteOrderHallPrice(OrderDetailsHall orderDetailsHall);
    
    //calculate
    public double calculateTotal(int orderId);
    
    //son
    public boolean deleteOrdersByCustomerId(int customerId);	
		
    public List<Order> getOrders();	
    Order getOrderById(int id);	
    List<Order> getOrdersByEmployeeId(int employeeId);	
    List<Order> getOrdersByCustomerId(int customerId);	
    public boolean getOrderByReceiptNumber(String receiptNumber);	
    public List<Order> searchOrders(Map<String, Object> findArgs);
}
