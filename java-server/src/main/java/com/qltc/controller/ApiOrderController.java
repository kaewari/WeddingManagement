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
import java.util.Objects;
import java.util.Set;
import javax.ws.rs.Consumes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    
    @PreAuthorize("hasAuthority('VIEW_ORDER')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrders(@RequestParam(name="fromDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date fromDate,
            @RequestParam(name = "toDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date toDate,
            @RequestParam(name = "isWedding", defaultValue = "false") Boolean isWedding,
            @RequestParam(name = "pageIndex", defaultValue = "1") Integer pageIndex,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return orderService.find(new HashMap<String, Object>() {{
            put("fromDate", fromDate);
            put("toDate", toDate);
            put("isWedding", isWedding);
            put("pageIndex", pageIndex);
            put("pageSize", pageSize);
        }});
    }
    
    @PreAuthorize("hasAuthority('VIEW_ORDER')")
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable("orderId") int orderId) {
        Order order = orderService.findById(orderId);
        if (order != null) {
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PreAuthorize("hasAuthority('ADD_NEW_ORDER')")
    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<Order> addNewOrder(@RequestBody Order order, Principal principal) {
        if (order == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        
        orderTotalCalculate(order);
        
        order.setStaff(userService.getUserByName(principal.getName()));
        
        if (orderService.addOrUpdate(order)) {
           return new ResponseEntity<>(order, HttpStatus.CREATED);
        } else {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PreAuthorize("hasAuthority('MODIFY_EXISTING_ORDER')")    
    @PutMapping("/{orderId}")
    public ResponseEntity updateExistingOrder(@PathVariable("orderId") int id, 
            @RequestBody Order order, Principal principal) {
        Order existing = orderService.findById(id);
        double total = 0;
        
        if (existing == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        if (!Double.isNaN(order.getDiscount())) existing.setDiscount(order.getDiscount());
        if (order.getNote() != null &&!order.getNote().isEmpty()) existing.setNote(order.getNote());
        if (order.getPaidVia()!= null && !order.getPaidVia().isEmpty()) existing.setPaidVia(order.getPaidVia());
        if (order.getReceiptNo() != null && !order.getReceiptNo().isEmpty()) existing.setReceiptNo(order.getReceiptNo());
        if (order.getCustomer() != null) existing.setCustomer(order.getCustomer());
        if (total > 0) existing.setTotal(total - existing.getDiscount());
        
        User user = userService.getUserByName(principal.getName());
        existing.setStaff(user);
        
        if (orderService.addOrUpdate(existing)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PreAuthorize("hasAuthority('DELETE_EXISTING_ORDER')")  
    @DeleteMapping("/{orderId}")
    public ResponseEntity deleteExistingOrder(@PathVariable("orderId") int id) {
        if (orderService.deleteById(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    
    @PreAuthorize("hasAnyAuthority('MODIFY_EXISTING_ORDER', 'MODIFY_EXISTING_WEDDING')") 
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
        
        orderTotalCalculate(order);
        
        if (orderService.addOrUpdate(order)) {
            return new ResponseEntity(request, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PreAuthorize("hasAnyAuthority('MODIFY_EXISTING_ORDER', 'MODIFY_EXISTING_WEDDING')") 
    @PostMapping("/{orderId}/remove-order-dish")
    public ResponseEntity deleteOrderDish(@PathVariable("orderId") int id,
            @RequestBody List<Integer> orderDishIds) {
        if (orderDishIds == null) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        Order order = orderService.findById(id);
        if (order == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        
        orderDishIds.forEach((orderDishId) -> {
                order.removeOrderDetailsDish(orderService.getOrderDetailsDishById(orderDishId));
        });
        orderTotalCalculate(order);
        
        if (orderService.addOrUpdate(order)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PreAuthorize("hasAnyAuthority('MODIFY_EXISTING_ORDER', 'MODIFY_EXISTING_WEDDING')")  
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
        orderTotalCalculate(order);
        
        if (orderService.addOrUpdate(order)) {
            return new ResponseEntity(request, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PreAuthorize("hasAnyAuthority('MODIFY_EXISTING_ORDER', 'MODIFY_EXISTING_WEDDING')") 
    @PostMapping("/{orderId}/remove-order-hall")
    public ResponseEntity deleteOrderHall(@PathVariable("orderId") int id,
            @RequestBody List<Integer> request) {
        if (request == null) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        Order order = orderService.findById(id);
        if (order == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        
        request.forEach((each) -> {
            order.removeOrderDetailsHall(orderService.getOrderDetailsHallById(each));
        });
        orderTotalCalculate(order);
        
        if (orderService.addOrUpdate(order)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PreAuthorize("hasAnyAuthority('MODIFY_EXISTING_ORDER', 'MODIFY_EXISTING_WEDDING')") 
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
        orderTotalCalculate(order);
        
        if (orderService.addOrUpdate(order)) {
            return new ResponseEntity(request, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PreAuthorize("hasAnyAuthority('MODIFY_EXISTING_ORDER', 'MODIFY_EXISTING_WEDDING')") 
    @PostMapping("/{orderId}/remove-order-service")
    public ResponseEntity deleteOrderService(@PathVariable("orderId") int id,
            @RequestBody List<Integer> request) {
        if (request == null) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        Order order = orderService.findById(id);
        if (order == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        
        request.forEach((each) -> {
            order.removeOrderDetailsService(orderService.getOrderDetailsServiceById(each));
        });
        orderTotalCalculate(order);
        
        if (orderService.addOrUpdate(order)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PreAuthorize("hasAuthority('PAY_ORDER')")
    @PostMapping("/{orderId}/pay")
    public ResponseEntity payOrder(@PathVariable("orderId") int id,
            @RequestBody Map<String, String> request, Principal principal) {
        Order order = orderService.findById(id);
        if (order == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        //{"receiptNo", "paidVia"}
        
        //check some thing here
        
        order.setReceiptNo(request.get("receiptNo"));
        order.setPaidVia(request.get("paidVia"));
        
        User currentUser = userService.getUserByName(principal.getName());
        if (!Objects.equals(order.getCustomer().getId(), currentUser.getId())) {
            order.setStaff(currentUser);
        }
        
        if (orderService.addOrUpdate(order)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }
    
    private void orderTotalCalculate(Order order) {
        double total = 0;
        double discount = 0;
        Set<OrderDetailsDish> orderDish = order.getOrderDetailsDishes();
        if (orderDish != null && !orderDish.isEmpty()) {
            for (OrderDetailsDish each : orderDish) {
                total += each.getQuantity() * each.getPrice() * (1 - each.getDiscount());
                discount += each.getPrice() * each.getDiscount() * each.getQuantity();
            }
        }
        Set<OrderDetailsHall> orderHall = order.getOrderDetailsHalls();
        if (orderHall != null && !orderHall.isEmpty()) {
            for (OrderDetailsHall each : orderHall) {
                total += each.getPrice() * (1 - each.getDiscount());
                discount += each.getPrice() * each.getDiscount();
            }
        }
        Set<OrderDetailsService> orderDetailsServices = order.getOrderDetailsServices();
        if (orderDetailsServices != null && !orderDetailsServices.isEmpty()) {
            for (OrderDetailsService each : orderDetailsServices) {
                total += each.getPrice() * each.getQuantity();
            }
        }
        order.setDiscount(discount);
        order.setTotal(total);
    }
}
