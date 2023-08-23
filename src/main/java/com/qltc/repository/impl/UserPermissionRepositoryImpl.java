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
    public List<Permission> getPermissionsByUserId(Integer userId) {
        Session session = this.factory.getObject().getCurrentSession();
//        CriteriaBuilder builder = session.getCriteriaBuilder();
//        CriteriaQuery<UserPermission> qUserPermission = builder.createQuery(UserPermission.class);
//        CriteriaQuery<Permission> qPermission = builder.createQuery(Permission.class);
//        Root<UserPermission> rootUserPermission = qUserPermission.from(UserPermission.class);
//        Root<Permission> rootPermission = qPermission.from(Permission.class);
//        qUserPermission.select(rootUserPermission).where(rootUserPermission.get("userId").in(userId));
//        qPermission.select(rootPermission).where(rootPermission.in(qUserPermission));
//        Query query = session.createQuery(qUserPermission);
        Query query = session.createQuery(
                "Select p.* "
                + "From Permission p "
                + "Where p.id in "
                + "(Select e.id "
                + "From UserPermission e "
                + "Where e.userId=:id)");
        query.setParameter("id", userId);

        return query.getResultList();
    }

    @Override
    public Boolean addOrUpdatePermissionsByUserId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean deleteUserPermissionById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean deleteUserPermissionsByUserId(Integer userId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean deleteUserPermissionsByPermissionId(Integer permissionId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Permission getPermissionById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
