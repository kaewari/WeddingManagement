/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.qltc.repository.impl;

import com.qltc.pojo.User;
import com.qltc.repository.UserRepository;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sonho
 */
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private LocalSessionFactoryBean factory;
    @Autowired
    private BCryptPasswordEncoder passEncoder;

    @Override
    public User getUserByName(String name) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("From User u Where u.name=:n");
        q.setParameter("n", name);

        return (User) q.getSingleResult();
    }

    @Override
    public User getUserById(Integer id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("From User u Where u.id=:id");
        q.setParameter("id", id);

        return (User) q.getSingleResult();
    }

    @Override
    public Boolean deleteUserById(Integer id) {
        Session s = this.factory.getObject().getCurrentSession();
        User p = this.getUserById(id);
        try {
            s.delete(p);
            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> getUsers() {
        Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("From User");

        return q.getResultList();
    }

    @Override
    public Boolean updateUser(User u) {
        Session s = this.factory.getObject().getCurrentSession();
        try {
            if (u.getId() == null) {
                s.save(u);
            } else {
                s.update(u);
            }

            return true;
        } catch (HibernateException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean authUser(String name, String password) {
        User u = this.getUserByName(name);
        return this.passEncoder.matches(password, u.getPassword());
    }

    @Override
    public User addUser(User user) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
