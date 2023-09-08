package com.qltc.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.qltc.pojo.Order;
import com.qltc.pojo.OrderDetailsDish;
import com.qltc.pojo.OrderDetailsHall;
import com.qltc.pojo.OrderDetailsService;
import com.qltc.pojo.User;
import com.qltc.pojo.Wedding;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import org.cloudinary.json.JSONArray;

public class OrderDeserializer extends StdDeserializer<Order>{
    
    public OrderDeserializer() {
        this(null);
    }
    
    public OrderDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Order deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode jnode = jp.getCodec().readTree(jp);
        
        Integer id = null;
        Double total = null;
        Double discount = null;
        String receiptNo = null;
        String paidVia = null;
        String note = null;
        Integer customerId = null;
        Integer staffId = null;
//        Wedding wedding = null;
//        List<OrderDetailsDish> orderDetailsDishes = null;
//        List<OrderDetailsHall> orderDetailsHalls = null;
//        List<OrderDetailsService> orderDetailsServices = null;
        
        Order newOne = new Order();
        ObjectMapper objectMapper = new ObjectMapper();
        
        if (jnode.get("id") != null) {
            id = jnode.get("id").intValue();
            newOne.setId(id); 
        }
        if (jnode.get("total") != null) {
            total = jnode.get("total").doubleValue();
            newOne.setTotal(total);
        }
        if (jnode.get("discount") != null) {
            discount =jnode.get("discount").doubleValue();
            newOne.setDiscount(discount);
        }
        if (jnode.get("receiptNo") != null) {
            receiptNo = jnode.get("receiptNo").asText();
            newOne.setReceiptNo(receiptNo);
        }
        if (jnode.get("paidVia") != null) {
            paidVia = jnode.get("paidVia").asText();
            newOne.setPaidVia(paidVia);
        }
        if (jnode.get("note") != null) {
            note = jnode.get("note").asText();
            newOne.setNote(note);
        }
        if (jnode.get("customer") != null) {
            customerId = jnode.get("customer").intValue();
            User user = new User();
            user.setId(customerId);
            newOne.setCustomer(user);
        }
        if (jnode.get("staff") != null) {
            staffId = jnode.get("staff").intValue();
            User user = new User();
            user.setId(staffId);
            newOne.setStaff(user);
        }
        if (jnode.get("wedding") != null) {
            String weddingJsonString = jnode.get("wedding").toString();
            Wedding wedding = objectMapper.readValue(weddingJsonString, Wedding.class);
            newOne.setWedding(wedding);
        }
        if (jnode.get("orderDetailsDishes") != null) {
            String orderDetailsDishesString = jnode.get("orderDetailsDishes").toString();
            JSONArray array = new JSONArray(orderDetailsDishesString);
            List<OrderDetailsDish> orderDetailsDishes = new LinkedList<>();
            
            for (int index = 0; index < array.length(); index++) {
                String orderDishString = array.get(0).toString();
                orderDetailsDishes.add(objectMapper.readValue(orderDishString, OrderDetailsDish.class));
            }
            
            newOne.setOrderDetailsDishes(new HashSet<>(orderDetailsDishes));   
        }
        if (jnode.get("orderDetailsHalls") != null) {            
            String orderDetailsHallsString = jnode.get("orderDetailsHalls").toString();
            JSONArray array = new JSONArray(orderDetailsHallsString);
            List<OrderDetailsHall> orderDetailsHalls = new LinkedList<>();
            
            for (int index = 0; index < array.length(); index++) {
                String orderHall = array.get(0).toString();
                orderDetailsHalls.add(objectMapper.readValue(orderHall, OrderDetailsHall.class));
            }
            
            newOne.setOrderDetailsHalls(new HashSet<>(orderDetailsHalls));
        }
        if (jnode.get("orderDetailsServices") != null) {
            String orderDetailsServicesString = jnode.get("orderDetailsServices").toString();
            JSONArray array = new JSONArray(orderDetailsServicesString);
            List<OrderDetailsService> orderDetailsServices = new LinkedList<>();
            
            for (int index = 0; index < array.length(); index++) {
                String orderService = array.get(0).toString();
                orderDetailsServices.add(objectMapper.readValue(orderService, OrderDetailsService.class));
            }
            
            newOne.setOrderDetailsServices(new HashSet<>(orderDetailsServices));
        }
        
        return newOne;
    }
}
