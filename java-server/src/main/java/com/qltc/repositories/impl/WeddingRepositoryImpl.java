package com.qltc.repositories.impl;

import com.qltc.pojo.Wedding;
import com.qltc.pojo.WeddingPicture;
import com.qltc.repositories.WeddingRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class WeddingRepositoryImpl implements  WeddingRepository {
    
    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public List<Wedding> findAll() {
        Session session = sessionFactory.getObject().getCurrentSession();
        Query query = session.createQuery("From Wedding");
        return query.getResultList();
    }

    @Override
    public Wedding findById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(Wedding.class, id);
    }

    @Override
    //find({"completed":"boolean", "onlyDeposit":"boolean", "allPaid":"boolean"});
    public List<Wedding> find(Map<String, Object> findArgs) {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Wedding> criteriaQuery = criteriaBuilder.createQuery(Wedding.class);
        Root<Wedding> weddingRoot = criteriaQuery.from(Wedding.class);
        
        List<Predicate> predicates = new ArrayList<>();
        
        Boolean isCompleted = (Boolean) findArgs.get("completed");
        if (isCompleted != null) {
            predicates.add(criteriaBuilder.equal(weddingRoot.get("isCompleted"), isCompleted));
        }
        
        Boolean onlyDeposit = (Boolean) findArgs.get("onlyDeposit");
        if (onlyDeposit != null) {
            predicates.add(criteriaBuilder.isNull(weddingRoot.get("totalLeft")));
        }
        
        Boolean allPaid = (Boolean) findArgs.get("allPaid");
        if (allPaid != null) {
            predicates.add(criteriaBuilder.isNotNull(weddingRoot.get("totalLeft")));
        }
        
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        
        TypedQuery<Wedding> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public boolean addOrUpdate(Wedding wedding) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            if (wedding.getId() != null) { //updating
                session.update(wedding);
            } else { //adding
                session.save(wedding);
            }
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }
    
    @Override
    public boolean addPictureToWedding(Wedding wedding, WeddingPicture picture) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            wedding.addWeddingPicture(picture);
            session.save(wedding);
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            Wedding existing = findById(id);
            if (existing != null) {
                session.delete(existing);
                return false;
            }
            return false;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean delete(Wedding wedding) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            session.delete((wedding));
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }
    
}
