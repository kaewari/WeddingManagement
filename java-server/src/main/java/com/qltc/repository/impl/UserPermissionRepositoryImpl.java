/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.repository.impl;

import com.qltc.pojo.Permission;
import com.qltc.repository.UserPermissionRepository;
import java.util.List;
import javax.persistence.Query;
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
public class UserPermissionRepositoryImpl implements UserPermissionRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Permission> getPermissionsByUserId(int userId) {
        Session s = this.factory.getObject().getCurrentSession();
        Query query = s.createNativeQuery("Select p.* From user_permission as up "
                + "Join permissions as p on up.permissionId = p.id Where up.userId = :userId and up.allows = 1");
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
    @Override
    public List<String> getPermissionStringsByUserId(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Query query = s.createNativeQuery("Select p.value From user_permission as up "
                + "Join permissions as p on up.permissionId = p.id "
                + "Join user_group_permission ugp on ugp.permissionId = p.id "
                + "Where up.userId = :userId and up.allows = 1");
        query.setParameter("userId", id);
        return query.getResultList();
    }
    
    @Override
    public Boolean addOrUpdatePermissionsByUserId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean deleteUserPermissionById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean deleteUserPermissionsByUserId(int userId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean deleteUserPermissionsByPermissionId(int permissionId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Permission getPermissionById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
