package com.qltc.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.qltc.pojo.Order;
import com.qltc.pojo.User;
import com.qltc.pojo.Wedding;
import java.io.IOException;
import java.util.Date;

public class WeddingDeserializer extends StdDeserializer<Wedding>{
    
    public WeddingDeserializer() {
        this(null);
    }
    
    public WeddingDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Wedding deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode jNode = jp.getCodec().readTree(jp);
        
        Integer id = null;
        Double deposit = null;
        Double totalLeft = null;
        Double discount = null;
        String paidVia = null;
        String receiptNo = null;
        Boolean isCompleted = null;
        Integer tableNumber = null;
        Integer guestNumber = null;
        String description = null;
        Date celebrityDate = null;
        Integer customerId = null;
        Integer userId = null;
        Integer orderId = null;
        
        Wedding newOne = new Wedding();
        
        if (jNode.get("id") != null) {
            id = jNode.get("id").intValue();
            newOne.setId(id);
        }
        if (jNode.get("deposit") != null) {
            deposit = jNode.get("deposit").doubleValue();
            newOne.setDeposit(deposit);
        }
        if (jNode.get("totalLeft") != null) {
            totalLeft = jNode.get("totalLeft").doubleValue();
            newOne.setTotalLeft(totalLeft);
        }
        if (jNode.get("discount") != null) {
            discount = jNode.get("discount").doubleValue();
            newOne.setDiscount(discount);
        }
        if (jNode.get("paidVia") != null) {
            paidVia = jNode.get("paidVia").asText();
            newOne.setPaidVia(paidVia);
        }
        if (jNode.get("receiptNo") != null) {
            receiptNo = jNode.get("receiptNo").asText();
            newOne.setReceiptNo(receiptNo);
        }
        if (jNode.get("isCompleted") != null) {
            isCompleted = jNode.get("isCompleted").booleanValue();
            newOne.setIsCompleted(isCompleted);
        }
        if (jNode.get("tableNumber") != null) {
            tableNumber = jNode.get("tableNumber").intValue();
            newOne.setTableNumber(tableNumber);
        }
        if (jNode.get("guestNumber") != null) {
            guestNumber = jNode.get("guestNumber").intValue();
            newOne.setGuestNumber(guestNumber);
        }
        if (jNode.get("description") != null) {
            description = jNode.get("description").asText();
            newOne.setDescription(description);
        }
        if (jNode.get("celebrityDate") != null) {
            celebrityDate = new Date(jNode.get("celebrityDate").longValue());
            newOne.setCelebrityDate(celebrityDate);
        }
        if (jNode.get("customer") != null) {
            customerId = jNode.get("customer").intValue();
            User customer = new User(customerId);
            newOne.setCustomer(customer);
        }
        if (jNode.get("user") != null) {
            userId = jNode.get("user").intValue();
            User user = new User(userId);
            newOne.setUser(user);
        }
        if (jNode.get("order") != null) {
            orderId = jNode.get("order").intValue();
            Order order = new Order();
            order.setId(orderId);
            newOne.setOrder(order);
        }
        
        return newOne;
    }
    
}
