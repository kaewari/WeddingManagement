package com.qltc.controller;

//some missing below about user (class)
import com.qltc.pojo.Wedding;
import com.qltc.pojo.WeddingPicture;
import com.qltc.pojo.WeddingServicePrice;
import com.qltc.service.WeddingService;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping //ok
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

    @GetMapping("/{weddingId}") //fail in user attribute
    public ResponseEntity<Wedding> getWeddingById(@PathVariable("weddingId") int id) {
        Wedding existing = weddingService.findWeddingById(id);
        if (existing != null) {
            return new ResponseEntity<>(existing, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping
//    public ResponseEntity<Wedding> addNewWedding(@RequestBody Map<String, Object> request) {
//        
//    }
    //update wedding
    @DeleteMapping("/{weddingId}")
    public ResponseEntity deleteWedding(@PathVariable("weddingId") int id) {
        if (weddingService.deleteWeddingById(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/service") //ok
    @ResponseStatus(HttpStatus.OK)
    public List<com.qltc.pojo.WeddingService> getAllServices() {
        return weddingService.findAllWeddingServices();
    }

    @GetMapping("/service/{serviceId}") //ok
    public ResponseEntity<com.qltc.pojo.WeddingService> getServiceById(@PathVariable("serviceId") int id) {
        com.qltc.pojo.WeddingService existing = weddingService.findWeddingServiceById(id);
        if (existing != null) {
            return new ResponseEntity<>(existing, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //error modifiedBy not be null
    @PostMapping("/service")
    public ResponseEntity<com.qltc.pojo.WeddingService> addNewWeddingService(@RequestBody com.qltc.pojo.WeddingService service) {
        if (weddingService.addOrUpdateWeddingService(service)) {
            return new ResponseEntity<>(service, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/service/{serviceId}") //ok
    public ResponseEntity updateExistingWeddingService(@PathVariable("serviceId") int id,
            @RequestBody com.qltc.pojo.WeddingService service) {
        com.qltc.pojo.WeddingService existing = weddingService.findWeddingServiceById(id);
        if (!service.getName().isEmpty()) {
            existing.setName(service.getName());
        }
        if (service.getIsAvailable() != null) {
            existing.setIsAvailable(service.getIsAvailable());
        }
        //set User to current user

        if (id == service.getId() && weddingService.addOrUpdateWeddingService(existing)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/service/{serviceId}/deactivate") //ok
    public ResponseEntity deactivateWeddingService(@PathVariable("serviceId") int id) {
        if (weddingService.deactiveWeddingServiceById(id)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/service/{serviceId}/activate")//ok
    public ResponseEntity activateWeddingService(@PathVariable("serviceId") int id) {
        if (weddingService.activeWeddingServiceById(id)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/service/{serviceId}") //ok
    public ResponseEntity deleteExistingWeddingService(@PathVariable("serviceId") int id) {
        com.qltc.pojo.WeddingService existing = weddingService.findWeddingServiceById(id);
        if (existing != null && weddingService.deleteWeddingServiceById(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/service/{serviceId}/add-service-price") //ok
    public ResponseEntity addNewServicePrice(@PathVariable("serviceId") int id,
            @RequestBody List<WeddingServicePrice> request) {
        com.qltc.pojo.WeddingService service = weddingService.findWeddingServiceById(id);
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

    @PostMapping("/service-price/{servicePriceId}") //ok
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

    @PostMapping("/service-price/{servicePriceId}/activate") //ok
    public ResponseEntity activateWeddingServicePrice(@PathVariable("servicePriceId") int id) {
        if (weddingService.activeWeddingServicePriceById(id)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/service-price/{servicePriceId}/deactivate") //ok
    public ResponseEntity deactivateWeddingServicePrice(@PathVariable("servicePriceId") int id) {
        if (weddingService.deactiveWeddingServicePriceById(id)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/service-price/{servicePriceId}") //ok
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

    @PostMapping("/{weddingId}/add-picture")
    @Transactional(propagation = Propagation.REQUIRED)
    public ResponseEntity<WeddingPicture> addNewPicture(@PathVariable("weddingId") int id,
            @RequestBody WeddingPicture weddingPicture) {
        Wedding existing = weddingService.findWeddingById(id);
        if (existing != null && weddingService.addPictureToWedding(existing, weddingPicture)) {
            return new ResponseEntity<>(weddingPicture, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/wedding-picture/{pictureId}")
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

    @DeleteMapping("/wedding-picture/{pictureId}")
    public ResponseEntity deleteWeddingPicture(@PathVariable("pictureId") int id) {
        if (weddingService.deleteWeddingPictureById(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
