package com.qltc.repository.impl;

import com.qltc.pojo.WeddingService;
import com.qltc.pojo.WeddingServicePrice;
import com.qltc.repository.WeddingServiceRepository;
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
public class WeddingServiceRepositoryImpl implements WeddingServiceRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public List<WeddingService> findAll() {
        Session session = sessionFactory.getObject().getCurrentSession();
        Query query = session.createQuery("From WeddingService");
        return query.getResultList();
    }

    @Override
    public WeddingService findById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(WeddingService.class, id);
    }

    @Override
    public boolean addOrUpdate(WeddingService service) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            if (service.getId() != null) { //updating
                session.update(service);
            } else { //adding
                session.save(service);
            }
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean addPriceToService(WeddingService service, List<WeddingServicePrice> prices) {
        Session session = sessionFactory.getObject().getCurrentSession();
        for (WeddingServicePrice price : prices) {
            service.addWeddingServicePrice(price);
        }
        session.update(service);
        return true;
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            WeddingService existing = findById(id);
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
    public boolean delete(WeddingService service) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            session.delete(service);
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

}
