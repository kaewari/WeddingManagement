package com.qltc.repository.impl;

import com.qltc.pojo.WeddingServicePrice;
import com.qltc.repository.WeddingServicePriceRepository;
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
public class WeddingServicePriceRepositoryImpl implements WeddingServicePriceRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public List<WeddingServicePrice> findAll() {
        Session session = sessionFactory.getObject().getCurrentSession();
        Query query = session.createQuery("From WeddingServicePrice");
        return query.getResultList();
    }

    @Override
    public WeddingServicePrice findById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(WeddingServicePrice.class, id);
    }

    @Override
    public boolean addOrUpdate(WeddingServicePrice price) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            if (price.getId() != null) {
                session.update(price);
            } else {
                session.save(price);
            }
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean deactivateOrActivateById(int id, boolean isAvailable) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            WeddingServicePrice existing = findById(id);
            if (existing != null) {
                existing.setIsAvailable(isAvailable);
                session.update(existing);
                return true;
            } else {
                return false;
            }
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean deactivateOrActivate(WeddingServicePrice price, boolean isAvailable) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            price.setIsAvailable(isAvailable);
            session.update(price);
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean deleteById(int id) {
        WeddingServicePrice existing = findById(id);
        return delete(existing);
    }

    @Override
    public boolean delete(WeddingServicePrice price) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            session.delete(price);
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

}
