package com.qltc.repositories.impl;

import com.qltc.pojo.Order;
import com.qltc.pojo.OrderDetailsDish;
import com.qltc.repositories.OrderRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
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
public class OrderRepositoryImpl implements OrderRepository {
    
    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public List<Order> findAll() {
        Session session = sessionFactory.getObject().getCurrentSession();
        Query query = session.createQuery("From Order");
        return query.getResultList();
    } 

    @Override
    public Order findById(long id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(Order.class, id);
    }

    @Override
    // find({"fromDateTime"="Date", "toDateTime"="Date", "isWedding"="boolean"})
    public List<Order> find(Map<String, Object> findArgs) {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root orderRoot = query.from(Order.class);
        Root dishDetailRoot = query.from(OrderDetailsDish.class);
        Join<?,?> joining = orderRoot.join("id");
        query.where(criteriaBuilder.equal(joining.get("id"), dishDetailRoot.get("orderId")));
        
        Date fromDateTime = (Date)findArgs.get("fromDateTime");
        Date toDateTime = (Date) findArgs.get("toDateTime");
        Boolean isWedding = (Boolean) findArgs.get("isWedding");
        
        List<Predicate> predicates = new ArrayList<>();
        
        if (fromDateTime != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(orderRoot.get("orderedDate"), fromDateTime));
        }
        
        if (toDateTime != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(orderRoot.get("orderedDate"), fromDateTime));
        } else { //if do not declare, make to present
            predicates.add(criteriaBuilder.lessThanOrEqualTo(orderRoot.get("orderedDate"), new Date()));
        }
        
        if (isWedding != null) {
            if(!isWedding) {
                predicates.add(criteriaBuilder.isNull(orderRoot.get("weddingId")));
            } else {
                predicates.add(criteriaBuilder.isNotNull(orderRoot.get("weddingId")));  
            }
        }
        
        query.where(predicates.toArray(new Predicate[0]));
        
        return (session.createQuery(query).getResultList());
    }

    @Override
    public boolean addOrUpdateOrder(Order order) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            if (order.getId() == null) {
                session.save(order);
            } else {
                session.update(order);
            }
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean deleteOrderById(int id) {
        Order order = findById(id);
        Session session = sessionFactory.getObject().getCurrentSession();
        session.delete(order);
        return true;
    }

    @Override
    public boolean deleteOrder(Order order) {
        Session session = sessionFactory.getObject().getCurrentSession();
        session.delete(order);
        return true;
    }
    
}
