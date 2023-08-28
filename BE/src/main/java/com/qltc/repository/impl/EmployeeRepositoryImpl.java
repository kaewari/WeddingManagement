/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.repository.impl;

import com.qltc.pojo.Employee;
import com.qltc.repository.EmployeeRepository;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.HibernateException;
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
    public Employee getEmployeeById(Integer id) {
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
    public boolean addOrUpdateEmployee(Employee e) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            if (e.getId() == null) {
                s.save(e);
            } else {
                s.update(e);
            }

            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteEmployee(Integer id) {
        Session s = this.factory.getObject().getCurrentSession();
        Employee p = this.getEmployeeById(id);
        try {
            s.delete(p);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
