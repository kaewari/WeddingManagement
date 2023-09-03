package com.qltc.services;

import com.qltc.pojo.Wedding;
import com.qltc.pojo.WeddingPicture;
import com.qltc.pojo.WeddingServicePrice;
import java.util.List;
import java.util.Map;

public interface WeddingService {
    public List<Wedding> findAllWeddings();
    public List<Wedding> findWeddings(Map<String, Object> findArgs);
    public List<com.qltc.pojo.WeddingService> findAllWeddingServices();
    public List<WeddingPicture> findAllWeddingPictures();
    public WeddingServicePrice getWeddingServicePriceById(int id);
    public Wedding findWeddingById(int id);
    public com.qltc.pojo.WeddingService findWeddingServiceById(int id);
    public WeddingPicture findWeddingPictureById(int id);
    public List<WeddingPicture> findWeddingPictureOfWedding(Wedding wedding);
    public List<WeddingPicture> findWeddingPictureOfWedding(int id);
    public com.qltc.pojo.WeddingService  addPriceToService(com.qltc.pojo.WeddingService service, List<WeddingServicePrice> prices);
    public boolean addOrUpdateWeddingService(com.qltc.pojo.WeddingService service);
    public boolean addPictureToWedding(Wedding wedding, WeddingPicture picture);
    public boolean deleteWeddingServiceById(int id);
    public WeddingServicePrice findWeddingServicePriceById(int id);
    public boolean addOrUpdateWeddingServicePrice(WeddingServicePrice servicePrice);
    public boolean deleteWeddingServicePriceById(int id);
    public boolean deactiveWeddingServiceById(int id);
    public boolean activeWeddingServiceById(int id);
    public boolean deactiveWeddingServicePriceById(int id);
    public boolean activeWeddingServicePriceById(int id);
    public boolean showOrHidePicture(WeddingPicture weddingPicture);
    public boolean deleteWeddingPictureById(int pictureId);
    public boolean deleteWeddingById(int weddingId);
}
