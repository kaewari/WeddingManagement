package com.qltc.service.impl;

import com.qltc.pojo.Wedding;
import com.qltc.pojo.WeddingPicture;
import com.qltc.pojo.WeddingServicePrice;
import com.qltc.repository.WeddingPictureRepository;
import com.qltc.repository.WeddingRepository;
import com.qltc.repository.WeddingServicePriceRepository;
import com.qltc.repository.WeddingServiceRepository;
import com.qltc.service.WeddingService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeddingServiceImpl implements WeddingService {

    @Autowired
    private WeddingRepository weddingRepo;

    @Autowired
    private WeddingServiceRepository weddingServiceRepo;

    @Autowired
    private WeddingServicePriceRepository priceRepo;

    @Autowired
    private WeddingPictureRepository weddingPictureRepo;

    @Override
    public List<Wedding> findAllWeddings() {
        return weddingRepo.findAll();
    }

    @Override
    public List<Wedding> findWeddings(Map<String, Object> findArgs) {
        return weddingRepo.find(findArgs);
    }

    @Override
    public List<com.qltc.pojo.WeddingService> findAllWeddingServices() {
        return weddingServiceRepo.findAll();
    }

    @Override
    public List<WeddingPicture> findAllWeddingPictures() {
        return weddingPictureRepo.findAll();
    }

    @Override
    public Wedding findWeddingById(int id) {
        return weddingRepo.findById(id);
    }

    @Override
    public com.qltc.pojo.WeddingService findWeddingServiceById(int id) {
        return weddingServiceRepo.findById(id);
    }

    @Override
    public WeddingServicePrice getWeddingServicePriceById(int id) {
        return priceRepo.findById(id);
    }

    @Override
    public WeddingPicture findWeddingPictureById(int id) {
        return weddingPictureRepo.findById(id);
    }

    @Override
    public List<WeddingPicture> findWeddingPictureOfWedding(Wedding wedding) {
        return weddingPictureRepo.findByWeddingId(wedding.getId());
    }

    @Override
    public List<WeddingPicture> findWeddingPictureOfWedding(int id) {
        return weddingPictureRepo.findByWeddingId(id);
    }

    @Override
    public boolean addOrUpdateWeddingService(com.qltc.pojo.WeddingService service) {
        return weddingServiceRepo.addOrUpdate(service);
    }

    @Override
    public com.qltc.pojo.WeddingService addPriceToService(com.qltc.pojo.WeddingService service, List<WeddingServicePrice> prices) {
        if (weddingServiceRepo.addPriceToService(service, prices)) {
            return service;
        } else {
            return null;
        }
    }

    @Override
    public boolean addPictureToWedding(Wedding wedding, WeddingPicture picture) {
        return weddingRepo.addPictureToWedding(wedding, picture);
    }

    @Override
    public boolean deleteWeddingServiceById(int id) {
        return weddingServiceRepo.deleteById(id);
    }

    @Override
    public WeddingServicePrice findWeddingServicePriceById(int id) {
        return priceRepo.findById(id);
    }

    @Override
    public boolean addOrUpdateWeddingServicePrice(WeddingServicePrice servicePrice) {
        return priceRepo.addOrUpdate(servicePrice);
    }

    @Override
    public boolean deleteWeddingServicePriceById(int id) {
        return priceRepo.deleteById(id);
    }

    @Override
    public boolean deactiveWeddingServiceById(int id) {
        com.qltc.pojo.WeddingService existing = weddingServiceRepo.findById(id);
        if (existing == null) {
            return false;
        }
        existing.setIsAvailable(false);
        return weddingServiceRepo.addOrUpdate(existing);
    }

    @Override
    public boolean activeWeddingServiceById(int id) {
        com.qltc.pojo.WeddingService existing = weddingServiceRepo.findById(id);
        if (existing == null) {
            return false;
        }
        existing.setIsAvailable(true);
        return weddingServiceRepo.addOrUpdate(existing);
    }

    @Override
    public boolean deactiveWeddingServicePriceById(int id) {
        return priceRepo.deactivateOrActivateById(id, false);
    }

    @Override
    public boolean activeWeddingServicePriceById(int id) {
        return priceRepo.deactivateOrActivateById(id, true);
    }

    @Override
    public boolean showOrHidePicture(WeddingPicture weddingPicture) {
        return weddingPictureRepo.showOrHide(weddingPicture, !weddingPicture.getIsPublic());
    }

    @Override
    public boolean deleteWeddingPictureById(int pictureId) {
        return weddingPictureRepo.deleteById(pictureId);
    }

    @Override
    public boolean deleteWeddingById(int weddingId) {
        return weddingRepo.deleteById(weddingId);
    }

}
