package com.qltc.repository.impl;

import com.qltc.pojo.Order;
import com.qltc.repository.OrderRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.Query;
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
public class OrderRepositoryImpl implements OrderRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    @Autowired
    private SimpleDateFormat dateFormat;

    @Override
    public List<Order> getOrders() {
        Session session = sessionFactory.getObject().getCurrentSession();
        Query query = session.createQuery("From Order");
        return query.getResultList();
    }

    @Override
    public Order getOrderById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(Order.class, id);
    }

    @Override
    // sreachOrders({"fromDateTime"="Date", "toDateTime"="Date", "isWedding"="boolean"})
    public List<Order> searchOrders(Map<String, Object> findArgs) {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root orderRoot = query.from(Order.class);
        query.select(orderRoot);
//        Root dishDetailRoot = query.from(OrderDetailsDish.class);
//        Join<?, ?> joining = orderRoot.join("id");
//        query.where(criteriaBuilder.equal(joining.get("id"), dishDetailRoot.get("orderId")));
//
        List<Predicate> predicates = new ArrayList<>();
        String fromDateTime = null;
        String toDateTime = null;
        if (findArgs != null) {
            try {
                fromDateTime = findArgs.get("fromDateTime").toString();
            } catch (Exception e) {
            }
            try {
                toDateTime = findArgs.get("toDateTime").toString();
            } catch (Exception e) {
            }
        }
        if (fromDateTime != null) {
            try {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(orderRoot.get("createdDate"), dateFormat.parse(fromDateTime)));
            } catch (ParseException ex) {
                Logger.getLogger(OrderRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (toDateTime != null) {
            try {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(orderRoot.get("createdDate"), dateFormat.parse(toDateTime)));
            } catch (ParseException ex) {
                Logger.getLogger(OrderRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else { //if do not declare, make to present
            Date currentDate = new Date();
            try {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(orderRoot.get("createdDate"), dateFormat.parse(currentDate.toString())));
            } catch (ParseException ex) {
                Logger.getLogger(OrderRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        query.where(predicates.toArray(Predicate[]::new));
        Query q = session.createQuery(query);
        return q.getResultList();
    }

    @Override
    public boolean addOrUpdateOrder(Order order) {

        try {
            Session session = sessionFactory.getObject().getCurrentSession();
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
        try {
            Session session = sessionFactory.getObject().getCurrentSession();
            try {
                Order order = getOrderById(id);
                if (order != null) {
                    session.delete(order);
                }
            } catch (NoResultException nre) {
                return false;
            }
            return true;
        } catch (HibernateException he) {
            return false;
        }
    }

    @Override
    public boolean deleteOrdersByCustomerId(int customerId) {
        try {
            Session session = sessionFactory.getObject().getCurrentSession();
            try {
                List<Order> orders = getOrdersByCustomerId(customerId);
                if (orders != null) {
                    orders.forEach(o -> {
                        session.delete(o);
                    });
                }
            } catch (NoResultException nre) {
                return false;
            }
            return true;
        } catch (HibernateException he) {
            return false;
        }
    }

    @Override
    public List<Order> getOrdersByEmployeeId(int employeeId) {
        try {
            Session session = sessionFactory.getObject().getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
            Root orderRoot = query.from(Order.class);
            query.select(orderRoot);
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(orderRoot.get("employee"), employeeId));
            query.where(predicates.toArray(Predicate[]::new));
            Query q = session.createQuery(query);
            return q.getResultList();
        } catch (HibernateException he) {
            return null;
        }
    }

    @Override
    public List<Order> getOrdersByCustomerId(int customerId) {
        try {
            Session session = sessionFactory.getObject().getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
            Root orderRoot = query.from(Order.class);
            query.select(orderRoot);
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(orderRoot.get("customer"), customerId));
            query.where(predicates.toArray(Predicate[]::new));
            Query q = session.createQuery(query);
            return q.getResultList();
        } catch (HibernateException he) {
            return null;
        }
    }

    @Override
    public boolean getOrderByReceiptNumber(String receiptNumber) {
        try {
            Session session = sessionFactory.getObject().getCurrentSession();
            Query query = session.createQuery("From Order Where receiptNo=:receiptNumber");
            query.setParameter("receiptNumber", receiptNumber);
            return query.getSingleResult() != null;
        } catch (NoResultException nre) {
            return false;
        }
    }
}
