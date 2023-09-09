package com.qltc.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.qltc.pojo.CustomerFeedback;
import com.qltc.pojo.User;
import java.io.IOException;

public class CustomerFeedbackDeserializer extends StdDeserializer<CustomerFeedback>{
    
    public CustomerFeedbackDeserializer() {
        this(null);
    }
    
    public CustomerFeedbackDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public CustomerFeedback deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        Integer id = null;
        String feedbackType = null;
        String comment = null;
        String reply = null;
        Integer customerId = null;
        Integer userId = null;
        
        if (node.get("id") != null) {
            id = node.get("id").intValue();
        }
        if (node.get("feedbackType") != null) {
            feedbackType = node.get("feedbackType").asText();
        }
        if (node.get("comment") != null) {
            comment = node.get("comment").asText();
        }
        if (node.get("reply") != null) {
            reply = node.get("reply").asText();
        }
        if (node.get("customer") != null) {
            customerId = node.get("customer").intValue();
        }
        if (node.get("user") != null) {
            userId = node.get("user").intValue();
        }
        
        CustomerFeedback customerFeedback = new CustomerFeedback();
        customerFeedback.setId(id);
        customerFeedback.setComment(comment);
        if (reply != null) customerFeedback.setComment(reply);
        customerFeedback.setFeedBackType(feedbackType);
        customerFeedback.setCustomer(new User(customerId));
        if (userId != null) customerFeedback.setUser(new User(userId));
        
        return customerFeedback;    
    }
    
}
