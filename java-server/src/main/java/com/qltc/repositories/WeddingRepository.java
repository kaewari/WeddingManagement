package com.qltc.repositories;

import com.qltc.pojo.Wedding;
import com.qltc.pojo.WeddingPicture;
import java.util.List;
import java.util.Map;

public interface WeddingRepository {
    public List<Wedding> findAll();
    public Wedding findById(int id);
    public List<Wedding> find(Map<String, Object> findArgs);
    public boolean addOrUpdate(Wedding wedding);
    public boolean addPictureToWedding(Wedding wedding, WeddingPicture picture); 
    public boolean deleteById(int id);
    public boolean delete(Wedding wedding);
}
