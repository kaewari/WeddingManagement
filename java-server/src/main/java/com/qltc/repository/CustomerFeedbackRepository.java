package com.qltc.repositories;

import com.qltc.pojo.CustomerFeedback;
import java.util.List;
import java.util.Map;

public interface CustomerFeedbackRepository {
    public List<CustomerFeedback> findAll();
    public CustomerFeedback findById(int id);
    public List<CustomerFeedback> find(Map<String, Object> findArgs);
    public boolean addOrUpdateCustomerFeedback(CustomerFeedback feedback);
    public boolean deleteCustomerFeedbackById(int id);
    public boolean deleteCustomerFeedback(CustomerFeedback feedback);
}
