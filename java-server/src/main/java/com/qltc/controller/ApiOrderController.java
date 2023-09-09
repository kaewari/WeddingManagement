package com.qltc.controller;

import com.qltc.pojo.Order;
import com.qltc.pojo.OrderDetailsDish;
import com.qltc.pojo.OrderDetailsHall;
import com.qltc.pojo.OrderDetailsService;
import com.qltc.pojo.User;
import com.qltc.service.OrderService;
import com.qltc.service.UserService;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class ApiOrderController {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrders(@RequestParam(name="fromDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fromDate,
            @RequestParam(name = "toDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date toDate,
            @RequestParam(name = "isWedding", defaultValue = "false") Boolean isWedding) {
        return orderService.find(new HashMap<String, Object>() {{
            put("fromDate", fromDate);
            put("toDate", toDate);
            put("isWedding", isWedding);
        }});
    }
    
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable("orderId") int orderId) {
        Order order = orderService.findById(orderId);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Order> addNewOrder(@RequestBody Order order, Principal principal) {
        double total = 0;
        Set<OrderDetailsDish> orderDish = order.getOrderDetailsDishes();
        if (!orderDish.isEmpty()) {
            for (OrderDetailsDish each : orderDish) {
                total += each.getQuantity() * each.getPrice() * (1 - each.getDiscount());
            }
        }
        Set<OrderDetailsHall> orderHall = order.getOrderDetailsHalls();
        if (!orderHall.isEmpty()) {
            for (OrderDetailsHall each : orderHall) {
                total += each.getPrice() * (1 - each.getDiscount());
            }
        }
        Set<OrderDetailsService> orderDetailsServices = order.getOrderDetailsServices();
        if (!orderDetailsServices.isEmpty()) {
            for (OrderDetailsService each : orderDetailsServices) {
                total += each.getPrice() * each.getQuantity();
            }
        }
        
        if (total > 0) order.setTotal(total - order.getDiscount());
        
        order.setStaff(userService.getUserByName(principal.getName()));
        
        if (order != null && orderService.addOrUpdate(order)) {
           return new ResponseEntity<>(order, HttpStatus.CREATED);
        } else {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    
    @PutMapping("/{orderId}")
    public ResponseEntity updateExistingOrder(@PathVariable("orderId") int id, 
            @RequestBody Order order, Principal principal) {
        Order existing = orderService.findById(id);
        double total = 0;
        
        Set<OrderDetailsDish> orderDish = order.getOrderDetailsDishes();
        if (orderDish != null && !orderDish.isEmpty()) {
            for (OrderDetailsDish each : orderDish) {
                total += each.getQuantity() * each.getPrice() * (1 - each.getDiscount());
            }
            existing.setOrderDetailsDishes(orderDish);
        }
        Set<OrderDetailsHall> orderHall = order.getOrderDetailsHalls();
        if (orderHall != null && !orderHall.isEmpty()) {
            for (OrderDetailsHall each : orderHall) {
                total += each.getPrice() * (1 - each.getDiscount());
            }
        }
        Set<OrderDetailsService> orderDetailsServices = order.getOrderDetailsServices();
        if (orderDetailsServices!= null && !orderDetailsServices.isEmpty()) {
            for (OrderDetailsService each : orderDetailsServices) {
                total += each.getPrice() * each.getQuantity();
            }
        }
        
        if (existing == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        if (!Double.isNaN(order.getDiscount())) existing.setDiscount(order.getDiscount());
        if (!order.getNote().isEmpty()) existing.setNote(order.getNote());
        if (!order.getPaidVia().isEmpty()) existing.setPaidVia(order.getPaidVia());
        if (!order.getReceiptNo().isEmpty()) existing.setReceiptNo(order.getPaidVia());
        if (order.getCustomer() != null) existing.setCustomer(order.getCustomer());
        if (total > 0) existing.setTotal(total - existing.getDiscount());
        
        User user = userService.getUserByName(principal.getName());
        order.setStaff(user);
        
        if (orderService.addOrUpdate(existing)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/{orderId}")
    public ResponseEntity deleteExistingOrder(@PathVariable("orderId") int id) {
        if (orderService.deleteById(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/{orderId}/add-order-dish")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity addOrderDish(@PathVariable("orderId") int id,
            @RequestBody List<OrderDetailsDish> request) {
        if (request == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Order order = orderService.findById(id);
        if (order == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        
        for (OrderDetailsDish dishDetail : request) {
            order.addOrderDetailsDish(dishDetail);
        }
        
        order.setTotal(orderService.calculateTotalOfOrderById(id));
        
        if (orderService.addOrUpdate(order)) {
            return new ResponseEntity(request, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/{orderId}/remove-order-dish")
    public ResponseEntity deleteOrderDish(@PathVariable("orderId") int id,
            @RequestBody List<Integer> request) {
        if (request == null) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        Order order = orderService.findById(id);
        if (order == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        
        for (Integer detailId : request) {
            if (!orderService.deleteOrderDetailsDishById(detailId)) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        order.setTotal(orderService.calculateTotalOfOrderById(id));
        orderService.addOrUpdate(order);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping("/{orderId}/add-order-hall")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity addOrderHall(@PathVariable("orderId") int id,
            @RequestBody List<OrderDetailsHall> request) {
        if (request == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Order order = orderService.findById(id);
        if (order == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        
        for (OrderDetailsHall detailHall : request) {
            order.addOrderDetailsHall(detailHall);
        }
        
        order.setTotal(orderService.calculateTotalOfOrderById(id));
        
        if (orderService.addOrUpdate(order)) {
            return new ResponseEntity(request, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/{orderId}/remove-order-hall")
    public ResponseEntity deleteOrderHall(@PathVariable("orderId") int id,
            @RequestBody List<Integer> request) {
        if (request == null) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        Order order = orderService.findById(id);
        if (order == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        
        for (Integer detailId : request) {
            if (!orderService.deleteOrderDetailsHallById(detailId)) {
                
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        order.setTotal(orderService.calculateTotalOfOrderById(id));
        orderService.addOrUpdate(order);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping("/{orderId}/add-order-service")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity addOrderService(@PathVariable("orderId") int id,
            @RequestBody List<OrderDetailsService> request) {
        if (request == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Order order = orderService.findById(id);
        if (order == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        
        for (OrderDetailsService detailService : request) {
            order.addOrderDetailsService(detailService);
        }
        
        order.setTotal(orderService.calculateTotalOfOrderById(id));
        
        if (orderService.addOrUpdate(order)) {
            return new ResponseEntity(request, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/{orderId}/remove-order-service")
    public ResponseEntity deleteOrderService(@PathVariable("orderId") int id,
            @RequestBody List<Integer> request) {
        if (request == null) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        Order order = orderService.findById(id);
        if (order == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        
        for (Integer detailId : request) {
            if (!orderService.deleteOrderDetailsServiceById(detailId)) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        order.setTotal(orderService.calculateTotalOfOrderById(id));
        orderService.addOrUpdate(order);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping("/{orderId}/pay")
    public ResponseEntity payOrder(@PathVariable("orderId") int id,
            @ModelAttribute Map<String, String> request) {
        Order order = orderService.findById(id);
        if (order == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        //{"receiptNo", "paidVia"}
        //check some thing here
        order.setReceiptNo(request.get("receiptNo"));
        order.setPaidVia(request.get("paidVia"));
        if (orderService.addOrUpdate(order)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
