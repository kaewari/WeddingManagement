/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.repository.impl;

import com.qltc.pojo.Branch;
import com.qltc.pojo.Employee;
import com.qltc.repository.EmployeeRepository;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sonho
 */
@Repository
@Transactional
public class EmployeeRepositoryImpl implements EmployeeRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public Employee getEmployeeById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("From Employee e Where e.id=:id");
        q.setParameter("id", id);

        return (Employee) q.getSingleResult();
    }

    @Override
    public List<Employee> getEmployees() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("From Employee");

        return q.getResultList();
    }

    @Override
    public Employee addEmployee(Employee e) {
        Session s = this.factory.getObject().getCurrentSession();
        s.save(e);
        return e;
    }

    @Override
    public boolean deleteEmployee(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Employee e = this.getEmployeeById(id);
        try {
            s.delete(e);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean updateEmployee(Employee e) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            s.update(e);
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }

    @Override
    public Employee getEmployeeByUserId(int userId) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<Employee> employee = b.createQuery(Employee.class);
            Root root = employee.from(Employee.class);
            employee.select(root);
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(b.equal(root.get("userId"), userId));
            employee.where(predicates.toArray(Predicate[]::new));
            Query q = s.createQuery(employee);
            return (Employee) q.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public Employee getEmployeeByIdentityNumber(String identityNumber) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("From Employee e Where e.identityNumber=:idN");
        q.setParameter("idN", identityNumber);
        try {
            return (Employee) q.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    public Branch getEmployeeByBranchId(int branchId) {

        try {
            Session s = this.factory.getObject().getCurrentSession();
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<Employee> employee = b.createQuery(Employee.class);
            Root root = employee.from(Employee.class);
            employee.select(root);
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(b.equal(root.get("branchId"), branchId));

            employee.where(predicates.toArray(Predicate[]::new));
            Query q = s.createQuery(employee);
            return (Branch) q.getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
