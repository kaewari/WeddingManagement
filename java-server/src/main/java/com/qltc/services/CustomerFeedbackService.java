package com.qltc.services;

import com.qltc.pojo.CustomerFeedback;
import com.qltc.pojo.User;
import java.util.List;
import java.util.Map;


public interface CustomerFeedbackService {
    public List<CustomerFeedback> findAll();
    public List<CustomerFeedback> findAllNoReply();
    public CustomerFeedback findById(int id);
    public List<CustomerFeedback> find(Map<String, Object> findArgs);
    public boolean addOrUpdateCustomerFeedback(CustomerFeedback feedback);
    //update message from customer
    public boolean updateCustomerFeedback(CustomerFeedback feedback, String alternation);
    //update talk back from user of system (admin/manager)
    public boolean replyOrUpdateCustomerFeedback(CustomerFeedback feedback, String message, User user);
    public boolean deleteCustomerFeedbackById(int id);
    public boolean deleteCustomerFeedback(CustomerFeedback feedback);
}
