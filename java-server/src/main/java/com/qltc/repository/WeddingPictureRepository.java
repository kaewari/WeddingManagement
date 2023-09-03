package com.qltc.repository;

import com.qltc.pojo.WeddingPicture;
import java.util.List;

public interface WeddingPictureRepository {

    public List<WeddingPicture> findAll();

    public WeddingPicture findById(int id);

    public List<WeddingPicture> findByWeddingId(int weddingId);

    public boolean addOrUpdate(WeddingPicture picture);

    public boolean showOrHideById(int id, boolean guestWillSee);

    public boolean showOrHide(WeddingPicture picture, boolean guestWillSee);

    public boolean deleteById(int id);

    public boolean delete(WeddingPicture picture);
}
