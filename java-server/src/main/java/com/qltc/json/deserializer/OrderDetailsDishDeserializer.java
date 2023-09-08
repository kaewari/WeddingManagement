package com.qltc.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.FloatNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.qltc.pojo.Dish;
import com.qltc.pojo.Order;
import com.qltc.pojo.OrderDetailsDish;
import java.io.IOException;

public class OrderDetailsDishDeserializer extends StdDeserializer<OrderDetailsDish>{

    public OrderDetailsDishDeserializer() {
        this(null);
    }
    
    public OrderDetailsDishDeserializer(Class<?> vc) {
        super(vc);
    }
    
    @Override
    public OrderDetailsDish deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode jnode = jp.getCodec().readTree(jp);
        
        Integer id = null;
        Integer quantity = null;
        Double price = null;
        Float discount = null;
        String note = null;
        Integer orderId = null;
        Integer dishId = null;
        
        OrderDetailsDish newOne = new OrderDetailsDish();
        
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
        if (jnode.get("discount") != null) {
            discount =jnode.get("discount").floatValue();
            newOne.setDiscount(discount);
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
        if (jnode.get("dish") != null) {
            dishId = jnode.get("dish").intValue();
            Dish dish = new Dish();
            dish.setId(dishId);
            newOne.setDish(dish);
        }
        
        return newOne;
    }
}
