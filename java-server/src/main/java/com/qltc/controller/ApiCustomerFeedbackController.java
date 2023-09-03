package com.qltc.controller;

//failed
import com.qltc.pojo.CustomerFeedback;
import com.qltc.pojo.User;
import com.qltc.service.CustomerFeedbackService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feedback")
public class ApiCustomerFeedbackController {

    @Autowired
    private CustomerFeedbackService feedbackService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK) //fail user0_.branchId
    public List<CustomerFeedback> getAll(@RequestParam(name = "replied", required = false) Boolean isReplied,
            @RequestParam(name = "customerId", required = false) Long customerId,
            @RequestParam(name = "userId", required = false) Long userId,
            @RequestParam(name = "pageIndex", defaultValue = "1") int pageIndex,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return feedbackService.find(new HashMap<String, Object>() {
            {
                put("replied", isReplied);
                put("customerId", customerId);
                put("userId", userId);
                put("pageIndex", pageIndex);
                put("pageSize", pageSize);
            }
        });
    }

    @GetMapping("/{feedbackId}") //fail
    public ResponseEntity<CustomerFeedback> getById(@PathVariable("feedbackId") int id) {
        CustomerFeedback feedback = feedbackService.findById(id);
        if (feedback != null) {
            return new ResponseEntity<>(feedback, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED) //not receive customerId
    public ResponseEntity<CustomerFeedback> addNewFeedback(@RequestBody CustomerFeedback customerFeedback) {
        System.out.println(customerFeedback.getCustomer());
        if (customerFeedback != null && feedbackService.addOrUpdateCustomerFeedback(customerFeedback)) {
            return new ResponseEntity<>(customerFeedback, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{feedbackId}/update")
    public ResponseEntity updateFeedbackFromCustomer(@PathVariable("feedbackId") int id,
            @RequestBody Map<String, String> request) {
        String feedbackString = request.get("message");
        CustomerFeedback feedback = feedbackService.findById(id);
        if (feedback != null && feedbackService.updateCustomerFeedback(feedback, feedbackString)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{feedbackId}/reply") //needed change to get current User
    public ResponseEntity replyFeedback(@PathVariable("feedbackId") int id,
            @RequestBody Map<String, String> request) {
        String feedbackString = request.get("message");
        CustomerFeedback feedback = feedbackService.findById(id);
        User currentUser = new User();
        if (feedback != null && feedbackService.replyOrUpdateCustomerFeedback(feedback, feedbackString, currentUser)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{feedbackId}")
    public ResponseEntity deleteFeedback(@PathVariable("feedbackId") int id) {
        if (feedbackService.deleteCustomerFeedbackById(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
