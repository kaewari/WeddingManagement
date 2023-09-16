/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.controller;

import com.qltc.pojo.Order;
import com.qltc.service.OrderService;
import com.qltc.service.RevenueService;
import com.qltc.service.UserService;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import org.cloudinary.json.JSONException;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sonho
 */
@RestController
@RequestMapping("/api")
public class ApiAdminController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private RevenueService revenueService;
    @Autowired
    private JSONObject message;

    @GetMapping(path = "/orders/", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<Object> getOrders() {
        return new ResponseEntity<>(this.orderService.getOrders(), HttpStatus.OK);
    }

    @GetMapping(path = "/order/id/{id}")
    @CrossOrigin
    public ResponseEntity<Object> getOrderById(@PathVariable int id) {
        return new ResponseEntity<>(this.orderService.getOrderById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/orders/employee-id/{employeeId}")
    @CrossOrigin
    public ResponseEntity<Object> getOrdersByEmployeeId(@PathVariable int employeeId) {
        return new ResponseEntity<>(this.orderService.getOrdersByEmployeeId(employeeId), HttpStatus.OK);
    }

    @GetMapping(path = "/orders/customer-id/{customerId}")
    @CrossOrigin
    public ResponseEntity<Object> getOrdersByCustomerId(@PathVariable int customerId) {
        return new ResponseEntity<>(this.orderService.getOrdersByCustomerId(customerId), HttpStatus.OK);
    }

    @PostMapping(path = "/orders/search/",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @CrossOrigin
    public ResponseEntity<Object> searchOrders(@RequestBody(required = false) Map<String, Object> findArgs) throws ParseException {
        try {
            return new ResponseEntity<>(this.orderService.searchOrders(findArgs), HttpStatus.OK);
        } catch (Exception e) {
            message.put("Msg", "Search failure");
            return new ResponseEntity<>(message.toString(), HttpStatus.OK);
        }
    }

    @PostMapping(path = "/orders/add/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<Object> addOrUpdateOrder(@RequestBody Map<String, String> order) {
        try {
            Order o = new Order();
            o.setCustomer(this.userService.getUserById(Integer.parseInt(order.get("customerId"))));
            o.setStaff(this.userService.getUserById(Integer.parseInt(order.get("employeeId"))));
            o.setDiscount(Double.parseDouble(order.get("discount")));
            o.setNote(order.get("note"));
            o.setTotal(Double.parseDouble(order.get("total")));
            o.setPaidVia(order.get("paidVia"));
            if (this.orderService.getOrderByReceiptNumber(order.get("receiptNo"))) {
                message.put("Msg", "This receipt number existed");
                return new ResponseEntity<>(message.toString(), HttpStatus.OK);
            } else {
                o.setReceiptNo(order.get("receiptNo"));
            }
            if (this.orderService.addOrUpdateOrder(o)) {
                message.put("Msg", "Add or update successfully");
                return new ResponseEntity<>(message.toString(), HttpStatus.OK);
            } else {
                message.put("Msg", "Add or update failure");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
            }
        } catch (NumberFormatException | JSONException e) {
            message.put("Msg", "Add or update failure");
            return new ResponseEntity<>(message.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/orders/delete/id/{id}")
    @CrossOrigin
    public ResponseEntity<Object> deleteOrderById(@PathVariable int id) {
        try {
            if (this.orderService.getOrderById(id) == null) {
                message.put("Msg", "This order does not exist");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
            }
            if (this.orderService.deleteOrderById(id)) {
                message.put("Msg", "Delete successfully");
                return new ResponseEntity<>(message.toString(), HttpStatus.OK);
            } else {
                message.put("Msg", "Delete failure");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
            }
        } catch (JSONException e) {
            message.put("Msg", "Delete failure");
            return new ResponseEntity<>(message.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/orders/delete/customer-id/{customerId}")
    @CrossOrigin
    public ResponseEntity<Object> deleteOrdersByCustomerId(@PathVariable int customerId) {
        try {
            List<Order> orders = this.orderService.getOrdersByCustomerId(customerId);
            if (orders == null || orders.isEmpty()) {
                message.put("Msg", "This customer does not have any orders or does not exist");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
            }
            if (this.orderService.deleteOrdersByCustomerId(customerId)) {
                message.put("Msg", "Delete successfully");
                return new ResponseEntity<>(message.toString(), HttpStatus.OK);
            } else {
                message.put("Msg", "Delete failure");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
            }
        } catch (JSONException e) {
            message.put("Msg", "Delete failure");
            return new ResponseEntity<>(message.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/stats/dishes/top/{limit}/", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<Object> getTopBestSellerDishes(@PathVariable int limit) {
        try {
            List<Object[]> list = this.revenueService.getTopBestSellerDishes(limit);
            if (list == null || list.isEmpty()) {
                message.put("Msg", "Don't have any value");
                return new ResponseEntity<>(message.toString(), HttpStatus.OK);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);

        } catch (JSONException e) {
            message.put("Msg", "Cannot get data");
            return new ResponseEntity<>(message.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/stats/services/top/{limit}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<Object> getTopBestSellerServices(@PathVariable int limit) {
        try {
            List<Object[]> list = this.revenueService.getTopBestSellerServices(limit);
            if (list == null || list.isEmpty()) {
                message.put("Msg", "Don't have any value");
                return new ResponseEntity<>(message.toString(), HttpStatus.OK);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);

        } catch (JSONException e) {
            message.put("Msg", "Cannot get data");
            return new ResponseEntity<>(message.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/stats/halls/top/{limit}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<Object> getTopBestSellerHalls(@PathVariable int limit) {
        try {
            List<Object[]> list = this.revenueService.getTopBestSellerHalls(limit);
            if (list == null || list.isEmpty()) {
                message.put("Msg", "Don't have any value");
                return new ResponseEntity<>(message.toString(), HttpStatus.OK);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);

        } catch (JSONException e) {
            message.put("Msg", "Cannot get data");
            return new ResponseEntity<>(message.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/stats/year/",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<Object> getStatsRevenueByYear(@RequestParam Map<String, String> params) {
        try {
            List<Object[]> list = this.revenueService.getStatsRevenueByYear(params);
            if (list == null || list.isEmpty()) {
                message.put("Msg", "Don't have any value");
                return new ResponseEntity<>(message.toString(), HttpStatus.OK);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);

        } catch (JSONException e) {
            message.put("Msg", "Cannot get data");
            return new ResponseEntity<>(message.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/stats/date/",
            consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<Object> getStatsRevenueByDateRange(@RequestParam Map<String, String> params) {
        try {
            List<Object[]> list = this.revenueService.getStatsRevenueByDateRange(params);
            if (list == null || list.isEmpty()) {
                message.put("Msg", "Don't have any value");
                return new ResponseEntity<>(message.toString(), HttpStatus.OK);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);

        } catch (JSONException e) {
            message.put("Msg", "Cannot get data");
            return new ResponseEntity<>(message.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
