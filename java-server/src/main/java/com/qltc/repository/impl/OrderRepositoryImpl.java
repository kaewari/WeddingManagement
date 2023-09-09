package com.qltc.repository.impl;

import com.qltc.pojo.Order;
import com.qltc.pojo.OrderDetailsDish;
import com.qltc.pojo.OrderDetailsHall;
import com.qltc.pojo.OrderDetailsService;
import com.qltc.pojo.Wedding;
import com.qltc.repository.OrderRepository;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
    public Order findById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(Order.class, id);
    }

    @Override
    // find({"fromDateTime"="Date", "toDateTime"="Date", "isWedding"="boolean", "pageIndex": "Integer", "pageSize": "Integer"})
    public List<Order> find(Map<String, Object> findArgs) {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root orderRoot = query.from(Order.class);

        Date fromDateTime = (Date) findArgs.get("fromDateTime");
        Date toDateTime = (Date) findArgs.get("toDateTime");
        Boolean isWedding = (Boolean) findArgs.get("isWedding");

        List<Predicate> predicates = new ArrayList<>();

        if (fromDateTime != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(orderRoot.<Date>get("createdDate"), fromDateTime));
        }

        if (toDateTime != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(orderRoot.<Date>get("createdDate"), toDateTime));
        } else { //if do not declare, make to present
            predicates.add(criteriaBuilder.lessThanOrEqualTo(orderRoot.<Date>get("createdDate"), new Date()));
        }

//        if (isWedding != null) {
//            if (!isWedding) {
//                predicates.add(joining.get("wedding").isNull());
//            } else {
//                predicates.add(joining.get("wedding").isNotNull());
//            }
//        }

        query.where(predicates.toArray(new Predicate[0]));
        query.select(orderRoot);
        Query q = session.createQuery(query);
        
        if (findArgs.get("pageIndex") != null && findArgs.get("pageSize") != null) {
            int pageIndex = (Integer) findArgs.get("pageIndex");
            int pageSize = (Integer) findArgs.get("pageSize");
            q.setFirstResult((pageIndex - 1) * pageSize);
            q.setMaxResults(pageSize);
        }

        return q.getResultList();
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
    public boolean addWeddingToOrder(Order order, Wedding wedding) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            order.setWedding(wedding);
            session.save(order);
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean deleteOrderById(int id) {
        Order order = findById(id);
        if (order == null) return false;
        Session session = sessionFactory.getObject().getCurrentSession();
        return deleteOrder(order);
    }

    @Override
    public boolean deleteOrder(Order order) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            session.delete(order);
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public OrderDetailsDish getOrderDetailsDishById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(OrderDetailsDish.class, id);
    }

    @Override
    public boolean addOrUpdateOrderDish(OrderDetailsDish orderDetailsDish) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            if (orderDetailsDish.getId() == null) {
                session.save(orderDetailsDish);
            } else {
                session.update(orderDetailsDish);
            }
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean deleteOrderDishById(int id) {
        OrderDetailsDish existing = getOrderDetailsDishById(id);
        if (existing == null) return false;
        return deleteOrderDish(existing);
    }

    @Override
    public boolean deleteOrderDish(OrderDetailsDish orderDetailsDish) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            session.delete(orderDetailsDish);
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public OrderDetailsService getOrderDetailsServiceById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(OrderDetailsService.class, id);
    }

    @Override
    public boolean addOrUpdateOrderService(OrderDetailsService orderDetailsService) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            if (orderDetailsService.getId() == null) {
                session.save(orderDetailsService);
            } else {
                session.update(orderDetailsService);
            }
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean deleteOrderServiceById(int id) {
        OrderDetailsService existing = getOrderDetailsServiceById(id);
        if (existing == null) return false;
        return deleteOrderService(existing);
    }

    @Override
    public boolean deleteOrderService(OrderDetailsService orderDetailsService) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            session.delete(orderDetailsService);
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public OrderDetailsHall getOrderDetailsHallById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(OrderDetailsHall.class, id);
    }

    @Override
    public boolean addOrUpdateOrderHallPrice(OrderDetailsHall orderDetailsHall) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            if (orderDetailsHall.getId() == null) {
                session.save(orderDetailsHall);
            } else {
                session.update(orderDetailsHall);
            }
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean deleteOrderHallPriceById(int id) {
        OrderDetailsHall existing = getOrderDetailsHallById(id);
        if (existing == null) return false;
        return deleteOrderHallPrice(existing);
    }

    @Override
    public boolean deleteOrderHallPrice(OrderDetailsHall orderDetailsHall) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            session.delete(orderDetailsHall);
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }
    
    @Override
    public boolean addDishToOrder(Order order, List<OrderDetailsDish> orderDetailsDishes) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            for (OrderDetailsDish orderDish : orderDetailsDishes) {
                order.addOrderDetailsDish(orderDish);
            }
            session.save(order);
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }
    
    @Override
    public boolean addServicePriceToOrder(Order order, List<OrderDetailsService> orderDetailsServices) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            for (OrderDetailsService orderService : orderDetailsServices) {
                order.addOrderDetailsService(orderService);
            }
            session.save(order);
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }
    
    @Override
    public boolean addHallPriceToOrder(Order order, List<OrderDetailsHall> orderDetailsHalls) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            for (OrderDetailsHall orderHall : orderDetailsHalls) {
                order.addOrderDetailsHall(orderHall);
            }
            session.save(order);
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }
    
    @Override
    @Transactional
    public boolean removeDishFromOrder(Order order, List<OrderDetailsDish> orderDetailsDishes){
        Session session = sessionFactory.getObject().getCurrentSession();
        Transaction tx = session.getTransaction();
        try {
            for (OrderDetailsDish orderDish : orderDetailsDishes) {
                order.removeOrderDetailsDish(orderDish);
                session.delete(orderDish);
            }
            tx.commit();
            return true;
        } catch (HibernateException e) {
            tx.rollback();
            return false;
        }
    }
    
    @Override
    @Transactional
    public boolean removeServicePriceFromOrder(Order order, List<OrderDetailsService> orderDetailsServices) {
        Session session = sessionFactory.getObject().getCurrentSession();
        Transaction tx = session.getTransaction();
        try {
            for (OrderDetailsService orderService : orderDetailsServices) {
                order.removeOrderDetailsService(orderService);
                session.delete(orderService);
            }
            tx.commit();
            return true;
        } catch (HibernateException e) {
            tx.rollback();
            return false;
        }
    }

    @Override
    @Transactional
    public boolean removeHallPriceFromOrder(Order order, List<OrderDetailsHall> orderDetailsHalls) {
        Session session = sessionFactory.getObject().getCurrentSession();
        Transaction tx = session.getTransaction();
        try {
            for (OrderDetailsHall orderHall : orderDetailsHalls) {
                order.removeOrderDetailsHall(orderHall);
                session.delete(orderHall);
            }
            tx.commit();
            return true;
        } catch (HibernateException e) {
            tx.rollback();
            return false;
        }
    }

    @Override
    public double calculateTotal(int orderId) {
        Session session = sessionFactory.getObject().getCurrentSession();
        String queryString = "SELECT SUM(total) FROM ("
            + "(SELECT SUM(od.quantity * od.price * (1-  od.discount)) as total FROM order_dish_details od WHERE orderId = :orderId) UNION"
            + "(SELECT SUM(oh.price * (1 - oh.discount)) as total FROM order_halls_details oh WHERE orderId = :orderId) UNION"
            + "(SELECT SUM(os.price * os.quantity) as total FROM order_services_details os WHERE orderId = :orderId)) TotalPriceTable";
        Query query = session.createNativeQuery(queryString);
        query.setParameter("orderId", orderId);
        return (Double) query.getSingleResult();
    }

}
