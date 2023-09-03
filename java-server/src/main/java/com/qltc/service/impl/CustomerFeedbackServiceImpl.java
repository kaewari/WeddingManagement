package com.qltc.service.impl;

import com.qltc.pojo.CustomerFeedback;
import com.qltc.pojo.User;
import com.qltc.repository.CustomerFeedbackRepository;
import com.qltc.service.CustomerFeedbackService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerFeedbackServiceImpl implements CustomerFeedbackService {

    @Autowired
    private CustomerFeedbackRepository customerFeedbackRepo;

    @Override
    public List<CustomerFeedback> findAll() {
        return customerFeedbackRepo.findAll();
    }

    @Override
    public List<CustomerFeedback> findAllNoReply() {
        return customerFeedbackRepo.find(new HashMap<String, Object>() {
            {
                put("replied", false);
            }
        });
    }

    @Override
    public CustomerFeedback findById(int id) {
        return customerFeedbackRepo.findById(id);
    }

    @Override
    public List<CustomerFeedback> find(Map<String, Object> findArgs) {
        return customerFeedbackRepo.find(findArgs);
    }

    @Override
    public boolean addOrUpdateCustomerFeedback(CustomerFeedback feedback) {
        return customerFeedbackRepo.addOrUpdateCustomerFeedback(feedback);
    }

    @Override
    public boolean updateCustomerFeedback(CustomerFeedback feedback, String alternation) {
        feedback.setComment(alternation);
        return feedback != null && customerFeedbackRepo.addOrUpdateCustomerFeedback(feedback);
    }

    @Override
    public boolean replyOrUpdateCustomerFeedback(CustomerFeedback feedback, String message, User user) {
        feedback.setReply(message);
        feedback.setUser(user);
        return feedback != null && customerFeedbackRepo.addOrUpdateCustomerFeedback(feedback);
    }

    @Override
    public boolean deleteCustomerFeedbackById(int id) {
        return customerFeedbackRepo.deleteCustomerFeedbackById(id);
    }

    @Override
    public boolean deleteCustomerFeedback(CustomerFeedback feedback) {
        return customerFeedbackRepo.deleteCustomerFeedback(feedback);
    }

}
