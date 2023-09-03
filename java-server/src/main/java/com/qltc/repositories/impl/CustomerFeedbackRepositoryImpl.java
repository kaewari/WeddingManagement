package com.qltc.repositories.impl;

import com.qltc.pojo.CustomerFeedback;
import com.qltc.repositories.CustomerFeedbackRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CustomerFeedbackRepositoryImpl implements CustomerFeedbackRepository {
    
    @Autowired
    private LocalSessionFactoryBean sessionFactory;
    
    @Autowired
    private Environment env;

    @Override
    public List<CustomerFeedback> findAll() {
        Session session = sessionFactory.getObject().getCurrentSession();
        Query query = session.createQuery("From CustomerFeedback fb Order by fb.id desc");
        return query.getResultList();
    }

    @Override
    public CustomerFeedback findById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(CustomerFeedback.class, id);
    }

    @Override
    // find({"replied"="boolean", "customerId":"long", "userId":"long", "pageIndex"="int", "pageSize"="int"})
    public List<CustomerFeedback> find(Map<String, Object> findArgs) {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<CustomerFeedback> query = criteriaBuilder.createQuery(CustomerFeedback.class);
        Root<CustomerFeedback> root = query.from(CustomerFeedback.class);
        
        List<Predicate> predicates = new ArrayList();
        
        if (findArgs.get("replied") != null) {
            if ((Boolean) findArgs.get("replied")) {
                predicates.add(criteriaBuilder.and(
                        criteriaBuilder.isNotNull(root.get("reply")),
                        criteriaBuilder.isNotEmpty(root.get("reply"))));
            } else {
                predicates.add(criteriaBuilder.and(
                        criteriaBuilder.isNull(root.get("reply")),
                        criteriaBuilder.isEmpty(root.get("reply"))));
            }
        }
        
        if (findArgs.get("customerId") != null) {
            Long customerId = (Long) findArgs.get("customerId");
            predicates.add(criteriaBuilder.equal(root.get("customer"), customerId));
        }
        
        if (findArgs.get("userId") != null) {
            Long userId = (Long) findArgs.get("userId");
            predicates.add(criteriaBuilder.equal(root.get("user"), userId));
        }
        
        query.where(predicates.toArray(new Predicate[0]));
        
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
    public boolean addOrUpdateCustomerFeedback(CustomerFeedback feedback) {
        if (feedback != null) {
            Session session = sessionFactory.getObject().getCurrentSession();
            try {
                if (feedback.getId() == null) { //adding
                    session.save(feedback);
                } else { //updating
                    session.update(feedback);
                }
            } catch (HibernateException e) {}
        }
        return false;
    }

    @Override
    public boolean deleteCustomerFeedbackById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            CustomerFeedback feedback = findById(id);
            session.delete(feedback);
            return true;
        } catch (Exception e) {}
        return false;
    }

    @Override
    public boolean deleteCustomerFeedback(CustomerFeedback feedback) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            session.delete(feedback);
            return true;
        } catch (Exception e) {}
        return false;
    }
    
}
