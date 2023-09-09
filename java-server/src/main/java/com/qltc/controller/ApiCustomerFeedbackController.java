package com.qltc.controller;

//failed
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qltc.pojo.CustomerFeedback;
import com.qltc.pojo.User;
import com.qltc.service.CustomerFeedbackService;
import com.qltc.service.UserService;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    
    @Autowired
    private UserService userService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List getAll(@RequestParam(name = "replied", required = false) Boolean isReplied,
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

    @GetMapping("/{feedbackId}")
    public ResponseEntity<CustomerFeedback> getById(@PathVariable("feedbackId") int id) {
        CustomerFeedback feedback = feedbackService.findById(id);
        if (feedback != null) {
            return new ResponseEntity<>(feedback, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ADD_NEW_FEEDBACK')")
    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<CustomerFeedback> addNewFeedback(@RequestBody Map<String, Object> request, Principal principal) {
        CustomerFeedback customerFeedback = new ObjectMapper().convertValue(request, CustomerFeedback.class);
        if (customerFeedback == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        User currentUser = userService.getUserByName(principal.getName());
        customerFeedback.setCustomer(currentUser);
        if (feedbackService.addOrUpdateCustomerFeedback(customerFeedback)) {
            return new ResponseEntity<>(customerFeedback, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_FEEDBACK')")
    @PostMapping("/{feedbackId}/update")
    public ResponseEntity updateFeedbackFromCustomer(@PathVariable("feedbackId") int id,
            @RequestBody Map<String, String> request, Principal principal) {
        String feedbackString = request.get("message");
        CustomerFeedback feedback = feedbackService.findById(id);
        if (feedback == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        User currentUser = userService.getUserByName(principal.getName());
        if (!Objects.equals(currentUser.getId(), feedback.getCustomer().getId())) return new ResponseEntity(HttpStatus.NOT_FOUND);
        
        if (feedbackService.updateCustomerFeedback(feedback, feedbackString)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_FEEDBACK')")
    @PostMapping("/{feedbackId}/reply")
    public ResponseEntity replyFeedback(@PathVariable("feedbackId") int id,
            @RequestBody Map<String, String> request, Principal principal) {
        String feedbackString = request.get("message");
        CustomerFeedback feedback = feedbackService.findById(id);
        if (feedback == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        User currentUser = userService.getUserByName(principal.getName());
        if (feedbackService.replyOrUpdateCustomerFeedback(feedback, feedbackString, currentUser)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('DELETE_EXISTING_FEEDBACK')")
    @DeleteMapping("/{feedbackId}")
    public ResponseEntity deleteFeedback(@PathVariable("feedbackId") int id) {
        if (feedbackService.deleteCustomerFeedbackById(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
