package com.qltc.repository.impl;

import com.qltc.pojo.HallPrice;
import com.qltc.repository.HallPriceRepository;
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
public class HallPriceRepositoryImpl implements HallPriceRepository {

    @Autowired
    LocalSessionFactoryBean sessionFactory;

    @Override
    public List<HallPrice> findAll() {
        Session session = sessionFactory.getObject().getCurrentSession();
        Query query = session.createQuery("From HallPrice");
        return query.getResultList();
    }

    @Override
    public HallPrice findById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(HallPrice.class, id);
    }

    @Override
    public HallPrice findByPeriod(String period) {
        Session session = sessionFactory.getObject().getCurrentSession();
        Query query = session.createQuery("From HallPrice hp Where hp.whatPeriod like %%?0%%");
        query.setParameter(0, period);
        query.setMaxResults(1);
        return (HallPrice) query.getResultList().get(0);
    }

    @Override
    public List<HallPrice> findByPrice(double fromPrice, double toPrice) {
        Session session = sessionFactory.getObject().getCurrentSession();
        Query query = session.createQuery("From HallPrice hp Where"
                + " hp.price >= ? and hp.price <= ? ");
        query.setParameter(0, fromPrice);
        query.setParameter(1, toPrice);
        return query.getResultList();

    }

    @Override
    public boolean addOrUpdate(HallPrice hallPrice) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            if (hallPrice.getId() != null) { //updating
                session.update(hallPrice);
            } else { //adding
                session.save(hallPrice);
            }
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            HallPrice existing = findById(id);
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
    public boolean delete(HallPrice hallPrice) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            session.delete(hallPrice);
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

}
