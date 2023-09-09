package com.qltc.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.annotation.JsonView;
import com.qltc.json.JsonMarkup;
import com.qltc.pojo.HallPrice;
import com.qltc.pojo.Order;
import com.qltc.pojo.OrderDetailsDish;
import com.qltc.pojo.OrderDetailsHall;
import com.qltc.pojo.OrderDetailsService;
import com.qltc.pojo.User;
import com.qltc.pojo.Wedding;
import com.qltc.pojo.WeddingPicture;
import com.qltc.pojo.WeddingServicePrice;
import com.qltc.service.OrderService;
import com.qltc.service.UserService;
import com.qltc.service.WeddingService;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.Consumes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wedding")
public class ApiWeddingController {

    @Autowired
    private WeddingService weddingService;
    
    @Autowired
    UserService userService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private Cloudinary cloudinary;

    @PreAuthorize("hasAuthority('VIEW_WEDDING')")
    @GetMapping
    @JsonView(JsonMarkup.CoreData.class)
    @ResponseStatus(HttpStatus.OK)
    public List<Wedding> getAllWedding(@RequestParam(name = "completed", required = false) Boolean completed,
            @RequestParam(name = "onlyDeposit", required = false) Boolean onlyDeposit,
            @RequestParam(name = "allPaid", required = false) Boolean allPaid) {
        return weddingService.findWeddings(new HashMap<String, Object>() {
            {
                put("completed", completed);
                put("onlyDeposit", onlyDeposit);
                put("allPaid", allPaid);
            }
        });
    }

    @PreAuthorize("hasAuthority('VIEW_WEDDING')")
    @GetMapping("/{weddingId}")
    public ResponseEntity<Wedding> getWeddingById(@PathVariable("weddingId") int id) {
        Wedding existing = weddingService.findWeddingById(id);
        if (existing != null) {
            return new ResponseEntity<>(existing, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PreAuthorize("hasAuthority('VIEW_WEDDING')")
    @GetMapping("/available-hall-price")
    @ResponseStatus(HttpStatus.OK)
    public List<HallPrice> getAvailableHallPrice(@RequestParam("inDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date inDate,
            @RequestParam("hallId") int hallId) {
        return weddingService.getAvailableHallPrice(inDate, hallId);
    }

    @PreAuthorize("hasAuthority('ADD_NEW_WEDDING')")
    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity addNewWedding(@RequestBody Order order) {
        Wedding wedding = order.getWedding();
        double total = 0;
        
        Set<OrderDetailsDish> orderDish = order.getOrderDetailsDishes();
        if (orderDish != null && !orderDish.isEmpty()) {
            for (OrderDetailsDish each : orderDish) {
                if (wedding != null && each.getQuantity() < wedding.getTableNumber()) {
                    each.setQuantity(wedding.getTableNumber() * each.getQuantity());
                }
                total += each.getQuantity() * each.getPrice() * (1 - each.getDiscount());
            }
        }
        Set<OrderDetailsHall> orderHall = order.getOrderDetailsHalls();
        if (orderHall != null && !orderHall.isEmpty()) {
            for (OrderDetailsHall each : orderHall) {
                total += each.getPrice() * (1 - each.getDiscount());
            }
        }
        Set<OrderDetailsService> orderDetailsServices = order.getOrderDetailsServices();
        if (orderDetailsServices != null && !orderDetailsServices.isEmpty()) {
            for (OrderDetailsService each : orderDetailsServices) {
                total += each.getPrice() * each.getQuantity();
            }
        }
        
        if (total > 0 && wedding != null) {
            order.setTotal(total - wedding.getDiscount());
            if (!Double.isNaN(wedding.getDeposit()) && wedding.getDeposit() > 0) {
                wedding.setTotalLeft(total - wedding.getDeposit());
            }
        } else {
            order.setTotal(total);
        }
                
        if (orderService.addOrUpdate(order)) {
            return new ResponseEntity<>(order.getWedding(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PreAuthorize("hasAuthority('MODIFY_EXISTING_WEDDING')")
    @PutMapping("/{weddingId}")
    public ResponseEntity updateExistingWedding(@PathVariable("weddingId") int id,
                @RequestBody Wedding wedding, Principal principal) {
        Wedding existing = weddingService.findWeddingById(id);
        if (existing == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        if (existing.getIsCompleted()) return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        
        Order order = existing.getOrder();
        
        if (wedding.getDescription() != null && !wedding.getDescription().isEmpty())
                existing.setDescription(wedding.getDescription());
        if (existing.getDiscount() != wedding.getDiscount()) {
            double total = existing.getDiscount() + order.getTotal() - wedding.getDiscount();
            existing.setDiscount(wedding.getDiscount());
            order.setTotal(total);
            existing.setOrder(order);
        }
        
        if (existing.getTotalLeft() > 0 && wedding.getPaidVia() != null && !wedding.getPaidVia().isEmpty()) existing.setPaidVia(wedding.getPaidVia());
        if (existing.getTotalLeft() > 0 && wedding.getReceiptNo()!= null && !wedding.getReceiptNo().isEmpty()) existing.setReceiptNo(wedding.getReceiptNo());
        
        if (wedding.getIsCompleted() != null) existing.setIsCompleted(wedding.getIsCompleted());
        if (wedding.getDeposit() > 0) {
            existing.setDeposit(wedding.getDeposit());
            order.setPaidVia(wedding.getPaidVia());
            order.setReceiptNo(wedding.getReceiptNo());
            existing.setOrder(order);
            existing.setTotalLeft(order.getTotal() - existing.getDeposit());
        }
        //check date
        if (wedding.getCelebrityDate() != null) existing.setCelebrityDate(wedding.getCelebrityDate());
        if (Integer.valueOf(wedding.getGuestNumber()) != null ) existing.setGuestNumber(wedding.getGuestNumber());
        if (Integer.valueOf(wedding.getTableNumber()) != null) existing.setTableNumber(wedding.getTableNumber());
        
        if (existing.getReceiptNo() != null && existing.getPaidVia() != null) {
            existing.setUser(userService.getUserByName(principal.getName()));
        }
        
        if  (weddingService.addOrUpdateWedding(existing)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PreAuthorize("hasAuthority('DELETE_EXISTING_WEDDING')")
    @DeleteMapping("/{weddingId}")
    public ResponseEntity deleteWedding(@PathVariable("weddingId") int id) {
        if (weddingService.deleteWeddingById(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(JsonMarkup.CoreData.class)
    @GetMapping("/service")
    @ResponseStatus(HttpStatus.OK)
    public List<com.qltc.pojo.WeddingService> getAllServices() {
        return weddingService.findAllWeddingServices();
    }

    @GetMapping("/service/{serviceId}")
    public ResponseEntity<com.qltc.pojo.WeddingService> getServiceById(@PathVariable("serviceId") int id) {
        com.qltc.pojo.WeddingService existing = weddingService.findWeddingServiceById(id);
        if (existing != null) {
            return new ResponseEntity<>(existing, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ADD_NEW_SERVICE')")
    @PostMapping("/service")
    @Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<com.qltc.pojo.WeddingService> addNewWeddingService(@ModelAttribute com.qltc.pojo.WeddingService service
                    , Principal principal) {
        User currentUser = userService.getUserByName(principal.getName());
        service.setUser(currentUser);
        if (weddingService.addOrUpdateWeddingService(service)) {
            return new ResponseEntity<>(service, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_SERVICE')")
    @PutMapping("/service/{serviceId}")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity updateExistingWeddingService(@PathVariable("serviceId") int id,
            @RequestBody com.qltc.pojo.WeddingService service, Principal principal) {
        com.qltc.pojo.WeddingService existing = weddingService.findWeddingServiceById(id);
        if (service.getName() != null && !service.getName().isEmpty()) {
            existing.setName(service.getName());
        }
        if (service.getIsAvailable() != null) {
            existing.setIsAvailable(service.getIsAvailable());
        }
        
        User currentUser = userService.getUserByName(principal.getName());
        existing.setUser(currentUser);
        

        if (weddingService.addOrUpdateWeddingService(existing)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_SERVICE')")
    @PostMapping("/service/{serviceId}/deactivate")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity deactivateWeddingService(@PathVariable("serviceId") int id) {
        if (weddingService.deactiveWeddingServiceById(id)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_SERVICE')")
    @PostMapping("/service/{serviceId}/activate")
    public ResponseEntity activateWeddingService(@PathVariable("serviceId") int id) {
        if (weddingService.activeWeddingServiceById(id)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('DELETE_EXISTING_SERVICE')")
    @DeleteMapping("/service/{serviceId}")
    public ResponseEntity deleteExistingWeddingService(@PathVariable("serviceId") int id) {
        com.qltc.pojo.WeddingService existing = weddingService.findWeddingServiceById(id);
        if (existing != null && weddingService.deleteWeddingServiceById(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_SERVICE')")
    @PostMapping("/service/{serviceId}/add-service-price")
    public ResponseEntity addNewServicePrice(@PathVariable("serviceId") int id,
            @RequestBody List<WeddingServicePrice> request, Principal principal) {
        com.qltc.pojo.WeddingService service = weddingService.findWeddingServiceById(id);
        User currentUser = userService.getUserByName(principal.getName());
        service.setUser(currentUser);
        if (service != null) {
            com.qltc.pojo.WeddingService response = weddingService.addPriceToService(service, request);
            if (response != null) {
                return new ResponseEntity(HttpStatus.CREATED);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_SERVICE')")
    @PutMapping("/service-price/{servicePriceId}")
    public ResponseEntity updateServicePrice(@PathVariable("servicePriceId") int id,
            @RequestBody WeddingServicePrice servicePrice) {
        WeddingServicePrice existing = weddingService.findWeddingServicePriceById(id);
        if (existing != null) {
            if (!servicePrice.getPeriod().isEmpty()) {
                existing.setPeriod(servicePrice.getPeriod());
            }
            if (servicePrice.getPrice() != null) {
                existing.setPrice(servicePrice.getPrice());
            }
            if (servicePrice.getIsAvailable() != null) {
                existing.setIsAvailable(servicePrice.getIsAvailable());
            }
            

            if (weddingService.addOrUpdateWeddingServicePrice(existing)) {
                return new ResponseEntity(HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_SERVICE')")
    @PostMapping("/service-price/{servicePriceId}/activate")
    public ResponseEntity activateWeddingServicePrice(@PathVariable("servicePriceId") int id) {
        if (weddingService.activeWeddingServicePriceById(id)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_SERVICE')")
    @PostMapping("/service-price/{servicePriceId}/deactivate")
    public ResponseEntity deactivateWeddingServicePrice(@PathVariable("servicePriceId") int id) {
        if (weddingService.deactiveWeddingServicePriceById(id)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_SERVICE')")
    @DeleteMapping("/service-price/{servicePriceId}")
    public ResponseEntity deleteWeddingServicePrice(@PathVariable("servicePriceId") int id) {
        WeddingServicePrice existing = weddingService.findWeddingServicePriceById(id);
        if (existing != null && weddingService.deleteWeddingServicePriceById(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/wedding-picture/{pictureId}")
    public ResponseEntity<WeddingPicture> getById(@PathVariable("pictureId") int id) {
        WeddingPicture picture = weddingService.findWeddingPictureById(id);
        if (picture != null) {
            return new ResponseEntity<>(picture, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_WEDDING')")
    @PostMapping("/{weddingId}/add-picture")
    @Consumes(MediaType.MULTIPART_FORM_DATA_VALUE)
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<WeddingPicture> addNewPicture(@PathVariable("weddingId") int id,
            @ModelAttribute WeddingPicture weddingPicture) {
        Wedding existing = weddingService.findWeddingById(id);
        
        //upload into cloudinary
        if (weddingPicture.getFile() != null) {
            try {
                Map<String, Object> result = cloudinary.uploader().upload(weddingPicture.getFile().getBytes(), 
                        ObjectUtils.emptyMap());
                weddingPicture.setPath((String) result.get("secure_url"));
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            }
        }
        
        if (existing != null && weddingService.addPictureToWedding(existing, weddingPicture)) {
            return new ResponseEntity<>(weddingPicture, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_WEDDING')")
    @PostMapping("/wedding-picture/{pictureId}/onoff")
    public ResponseEntity reverseStateOfPicture(@PathVariable("pictureId") int id) {
        WeddingPicture existing = weddingService.findWeddingPictureById(id);
        if (existing != null && weddingService.showOrHidePicture(existing)) {
            return new ResponseEntity(new HashMap<String, Boolean>() {
                {
                    put("isPublic", existing.getIsPublic());
                }
            }, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_WEDDING')")
    @DeleteMapping("/wedding-picture/{pictureId}")
    public ResponseEntity deleteWeddingPicture(@PathVariable("pictureId") int id) {
        if (weddingService.deleteWeddingPictureById(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
    
    @PreAuthorize("hasAuthority('PAY_WEDDING')")
    @PostMapping("/{weddingOrderId}/pay")
    public ResponseEntity payWeddingOrder(@PathVariable("weddingOrderId") int id,
            @RequestBody Map<String, Object> request) {
        Wedding wedding = weddingService.findWeddingById(id);
        if (wedding == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        Order order = wedding.getOrder();
        
        //request {"deposit": "Double", "completed": "Boolean", "receiptNo": "String", "paidVia": "String"}
        Double deposit = null;
        if (request.get("deposit") != null) deposit = Double.valueOf(request.get("deposit").toString());
        Boolean completed = (Boolean) request.get("allPaid");
        String receiptNo = (String) request.get("receiptNo");
        String paidVia = (String) request.get("paidVia");
        if (deposit != null) {
            //pay for deposit
            wedding.setDeposit(deposit);
            wedding.setTotalLeft(order.getTotal() - deposit);
            order.setCreatedDate(new Date());
            if (receiptNo != null && !receiptNo.isEmpty()) order.setReceiptNo(receiptNo);
            order.setPaidVia(paidVia);
            order.setWedding(wedding);
            if (orderService.addOrUpdate(order)) {
                return new ResponseEntity(HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
            }
        } else if (completed != null) {
            //pay for totalLeft
            wedding.setTotalLeft(order.getTotal() - wedding.getDeposit());
            wedding.setPaidVia(paidVia);
            wedding.setReceiptNo(receiptNo);
            order.setWedding(wedding);
            if (orderService.addOrUpdate(order)) {
                return new ResponseEntity(HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
            }
        }
        
        return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
    }
    
    @PreAuthorize("hasAuthority('MODIFY_EXISTING_WEDDING')")
    @PostMapping("{weddingId}/all-done")
    public ResponseEntity setDoneForWedding(@PathVariable("weddingId") Integer id) {
        Wedding wedding = weddingService.findWeddingById(id);
        if (wedding == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        if (!new Date().after(wedding.getCelebrityDate())) {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
        wedding.setIsCompleted(true);
        if (weddingService.addOrUpdateWedding(wedding)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
    }
    
    //only admin
    
    @PreAuthorize("hasAuthority('ADMIN_SPECIAL_WEDDING')")
    @PostMapping("{weddingId}/all-done-reverse")
    public ResponseEntity setDoneForWeddingReverse(@PathVariable("weddingId") Integer id) {
        Wedding wedding = weddingService.findWeddingById(id);
        if (wedding == null) return new ResponseEntity(HttpStatus.NOT_FOUND);
        wedding.setIsCompleted(false);
        if (weddingService.addOrUpdateWedding(wedding)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
    }
}
