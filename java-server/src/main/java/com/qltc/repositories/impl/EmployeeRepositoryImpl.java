package com.qltc.repositories.impl;

import com.qltc.pojo.Employee;
import com.qltc.pojo.User;
import com.qltc.repositories.EmployeeRepository;
import java.util.ArrayList;
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
public class EmployeeRepositoryImpl implements EmployeeRepository {
    
    @Autowired
    LocalSessionFactoryBean sessionFactory;

    @Override
    //findAll({"branchId": "Integer", "keyword": "String", "pageIndex": "Integer", "pageSize": "Integer"});
    public List<Employee> findAll(Map<String, Object> findArgs) {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
        Root root = query.from(Employee.class);
        Join<User, Employee> joins = root.join("user");
        
        List<Predicate> predicates = new ArrayList<>();
        Integer branchId = (Integer) findArgs.get("branchId");
        if (branchId != null) {
            predicates.add(builder.equal(root.get("branch"), branchId));
        }
        String keyword = (String) findArgs.get("keyword");
        if (!keyword.isEmpty()) {
            predicates.add(builder.like(builder.lower(root.get("firstName")),
                    String.format("%%%s%%", keyword.toLowerCase())));
            predicates.add(builder.like(builder.lower(root.get("lastName")),
                    String.format("%%%s%%", keyword.toLowerCase())));
            predicates.add(builder.like(builder.lower(joins.get("name")),
                    String.format("%%%s%%", keyword.toLowerCase())));
            predicates.add(builder.like(builder.lower(joins.get("email")),
                    String.format("%%%s%%", keyword.toLowerCase())));
            predicates.add(builder.like(builder.lower(joins.get("phone")),
                    String.format("%%%s%%", keyword.toLowerCase())));
        }
        
        Query q = session.createQuery(query);
        if (findArgs.get("pageIndex") != null && findArgs.get("pageSize") != null) {
            int pageSize = (Integer) findArgs.get("pageSize");
            int pageIndex = (Integer) findArgs.get("pageIndex");
            q.setFirstResult((pageIndex - 1) * pageSize);
            q.setMaxResults(pageSize);
        }
        return q.getResultList();
        
    }

    @Override
    public Employee findById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(Employee.class, id);
    }

    @Override
    public boolean addOrUpdate(Employee employee) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            if (employee.getId() == null) {
                session.save(employee);
            } else {
                session.update(employee);
            }
            return true;
        } catch (HibernateException e) {
           return false;
        }
    }

    @Override
    public boolean deleteById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        Employee employee = findById(id);
        if (employee != null) {
            return delete(employee);
        }
        return false;
    }

    @Override
    public boolean delete(Employee employee) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            session.delete(employee);
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }
    
}
