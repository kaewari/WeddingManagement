package com.qltc.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.qltc.pojo.Order;
import com.qltc.pojo.OrderDetailsService;
import com.qltc.pojo.WeddingServicePrice;
import java.io.IOException;

public class OrderDetailsServiceDeserializer extends StdDeserializer<OrderDetailsService>{
    
    public OrderDetailsServiceDeserializer() {
        this(null);
    }
    
    public OrderDetailsServiceDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public OrderDetailsService deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode jnode = jp.getCodec().readTree(jp);
        
        Integer id = null;
        Integer quantity = null;
        Double price = null;
        String note = null;
        Integer orderId = null;
        Integer servicePriceId = null;
        
        OrderDetailsService newOne = new OrderDetailsService();
        
        if (jnode.get("id") != null) {
            id = jnode.get("id").intValue();
            newOne.setId(id);
        }
        if (jnode.get("quantity") != null) {
            quantity = jnode.get("quantity").intValue();
            newOne.setQuantity(quantity);
        }
        if (jnode.get("price") != null) {
            price = jnode.get("price").doubleValue();
            newOne.setPrice(price);
        }
        if (jnode.get("note") != null) {
            note = jnode.get("note").asText();
            newOne.setNote(note);
        }
        if (jnode.get("order") != null) {
            orderId = jnode.get("order").intValue();
            Order order = new Order();
            order.setId(orderId);
            newOne.setOrder(order);
        }
        if (jnode.get("servicePrice") != null) {
            servicePriceId = jnode.get("servicePrice").intValue();
            WeddingServicePrice servicePrice = new WeddingServicePrice();
            servicePrice.setId(servicePriceId);
            newOne.setServicePrice(servicePrice);
        }
        
        return newOne;
    }
    
}
