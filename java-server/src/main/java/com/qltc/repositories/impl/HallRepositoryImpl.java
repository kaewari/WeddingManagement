package com.qltc.repositories.impl;

import com.qltc.pojo.Branch;
import com.qltc.pojo.Hall;
import com.qltc.pojo.HallPrice;
import com.qltc.repositories.HallRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateError;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class HallRepositoryImpl implements  HallRepository {
    
    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public List<Hall> findAll() {
        Session session = sessionFactory.getObject().getCurrentSession();
        Query query = session.createQuery("From Hall h left join fetch h.hallPrice");
        return query.getResultList();
    }

    @Override
    public Hall findById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(Hall.class, id);
    }
    
    @Override
    // find(new Map<String, Object>() {"minPrice"=double, "maxPrice"=double})
    public List<Hall> find(Map<String, Object> findArgs) {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Hall> query = criteriaBuilder.createQuery(Hall.class);
        Root<Hall> root = query.from(Hall.class);
        Root<HallPrice> rootPrice = query.from(HallPrice.class);
        
        double minPrice = (double) findArgs.get("minPrice");
        double maxPrice = (double) findArgs.get("maxPrice");
        
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.greaterThanOrEqualTo(rootPrice.get("price"), minPrice));
        predicates.add(criteriaBuilder.lessThanOrEqualTo(rootPrice.get("price"), minPrice));
        
        query.where(predicates.toArray(new Predicate[0]));
        query.multiselect(root);
        
        return session.createQuery(query).getResultList();
    }
    
    
    @Override
    public List<Hall> findAllInBranch(Branch branch) {
        Session session = sessionFactory.getObject().getCurrentSession();
        int branchId = branch.getId();
        Query query = session.createQuery("From Branch b Where b.branchId = :branchId");
        query.setParameter("branchId", branchId);
        return query.getResultList();
    }

    @Override
    public boolean addOrUpdateHall(Hall hall) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            if (hall.getId() == null) {
                session.save(hall);
            } else {
                session.update(hall);
            }
            return true;
        } catch (HibernateError e) {
            return false;
        }
    }

    @Override
    public boolean deactivateOrActivateHallById(int id, boolean isActive) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            Query query = session.createQuery("UPDATE Hall h SET h.isActive = ?1 Where h.id = ?2");
            query.setParameter(1, isActive);
            query.setParameter(2, id);
            query.executeUpdate();
            return true;
        }
        catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean deactivateOrActivateHall(Hall hall, boolean isActive) {
        return deactivateOrActivateHallById(hall.getId(), isActive);
    }

    @Override
    public boolean deleteHallById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        Hall existing = findById(id);
        if (existing != null) {
            existing.setIsActive(false);
            session.delete(existing);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteHall(Hall hall) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            hall.setIsActive(false);
            session.delete(hall);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
}
