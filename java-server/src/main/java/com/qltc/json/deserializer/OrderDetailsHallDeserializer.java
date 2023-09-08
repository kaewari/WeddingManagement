package com.qltc.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.FloatNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.qltc.pojo.HallPrice;
import com.qltc.pojo.Order;
import com.qltc.pojo.OrderDetailsHall;
import java.io.IOException;

public class OrderDetailsHallDeserializer extends StdDeserializer<OrderDetailsHall> {

    public OrderDetailsHallDeserializer() {
        this(null);
    }
    
    public OrderDetailsHallDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public OrderDetailsHall deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode jnode = jp.getCodec().readTree(jp);
        
        Integer id = null;
        Double price = null;
        Float discount = null;
        Integer orderId = null;
        Integer hallPriceId = null;
        
        OrderDetailsHall newOne = new OrderDetailsHall();
        
        if (jnode.get("id") != null) {
            id = jnode.get("id").intValue();
            newOne.setId(id);
        }
        if (jnode.get("price") != null) {
            price = jnode.get("price").doubleValue();
            newOne.setPrice(price);
        }
        if (jnode.get("discount") != null) {
            discount = jnode.get("discount").floatValue();
            newOne.setDiscount(discount);
        }
        if (jnode.get("order") != null) {
            orderId = jnode.get("order").intValue();
            Order order = new Order();
            order.setId(orderId);
            newOne.setOrder(order);
        }
        if (jnode.get("hallPrice") != null) {
            hallPriceId = jnode.get("hallPrice").intValue();
            HallPrice hallPrice = new HallPrice();
            hallPrice.setId(hallPriceId);
            newOne.setHallPrice(hallPrice);
        }
        
        return newOne;
    }
    
}
