package com.qltc.repositories.impl;

import com.qltc.pojo.WeddingPicture;
import com.qltc.repositories.WeddingPictureRepository;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class WeddingPictureRepositoryImpl implements  WeddingPictureRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    
    @Override
    public List<WeddingPicture> findAll() {
        Session session = sessionFactory.getObject().getCurrentSession();
        Query query = session.createQuery("From WeddingPicture");
        return query.getResultList();
    }

    @Override
    public WeddingPicture findById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(WeddingPicture.class, id);
    }

    @Override
    public List<WeddingPicture> findByWeddingId(int weddingId) {
        Session session = sessionFactory.getObject().getCurrentSession();
        Query query = session.createQuery("From WeddingPicture wp Where wp.weddingId = ?");
        query.setParameter(0, weddingId);
        return query.getResultList();
    }

    @Override
    public boolean addOrUpdate(WeddingPicture picture) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            if (picture.getId() != null) { //updating
                session.update(picture);
            } else { //adding
                session.save(picture);
            }
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }
    
    @Override
    public boolean showOrHide(WeddingPicture picture, boolean guestWillSee) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            picture.setIsPublic(guestWillSee);
            session.update(picture);
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }
    
    @Override
    public boolean showOrHideById(int id, boolean guestWillSee) {
        WeddingPicture existing = findById(id);
        if (existing != null) {
            return showOrHide(existing, guestWillSee);
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            WeddingPicture existing = findById(id);
            if (existing != null) {
                session.delete(existing);
                return true;
            }
            return false;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean delete(WeddingPicture picture) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            session.delete(picture);
            return true;
        } catch (HibernateException e) {
            return true;
        }
    }
    
}
