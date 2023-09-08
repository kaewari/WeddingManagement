package com.qltc.repository.impl;

import com.qltc.pojo.Permission;
import com.qltc.pojo.UserPermission;
import com.qltc.repository.UserPermissionRepository;
import java.util.List;
import javax.persistence.NoResultException;
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
public class UserPermissionRepositoryImpl implements UserPermissionRepository {

    @Autowired
    private LocalSessionFactoryBean factory;

    @Override
    public List<Permission> getPermissionsOfUserByUserId(int userId) {
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
    public UserPermission getUserPermissionById(int id) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            Query query = s.createQuery("From UserPermission u Where u.id =:id");
            query.setParameter("id", id);
            return (UserPermission) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public UserPermission addUserPermissionsByUserId(UserPermission userPermisison) {

        try {
            Session s = this.factory.getObject().getCurrentSession();
            s.save(userPermisison);
            return userPermisison;
        } catch (HibernateException e) {
            return null;
        }
    }

    @Override
    public boolean updateUserPermissionById(UserPermission userPermisison) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            s.update(userPermisison);
            return true;
        } catch (HibernateException he) {
            return false;
        }
    }

    @Override
    public boolean deleteUserPermissionById(int id) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            UserPermission p = this.getUserPermissionById(id);
            s.delete(p);
            return true;
        } catch (HibernateException he) {
            return false;
        }
    }

    @Override
    public boolean deleteUserPermissionsByUserId(int userId) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            List<UserPermission> listP = this.getUserPermissionsByUserId(userId);
            listP.forEach(l -> {
                s.delete(l);
            });
            return true;
        } catch (HibernateException he) {
            return false;
        }
    }

    @Override
    public boolean deleteUserPermissionsByPermissionId(int permissionId) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            List<UserPermission> listP = this.getUserPermissionsByPermissionId(permissionId);
            listP.forEach(l -> {
                s.delete(l);
            });

            return true;
        } catch (HibernateException he) {
            return false;
        }
    }

    @Override
    public List<UserPermission> getUserPermissionsByUserId(int userId) {
        Session s = this.factory.getObject().getCurrentSession();
        String queryString = "Select * From user_permission Where userId=?1";
        Query query = s.createNativeQuery(queryString, UserPermission.class)
                .setParameter(1, userId);
        return query.getResultList();
    }

    @Override
    public List<UserPermission> getUserPermissionsByPermissionId(int permissionId) {
        Session s = this.factory.getObject().getCurrentSession();
        String queryString = "Select * From user_permission Where permissionId=?1";
        Query query = s.createNativeQuery(queryString, UserPermission.class)
                .setParameter(1, permissionId);
        return query.getResultList();
    }

    @Override
    public UserPermission checkExistUserPermission(int userId, int permissionId) {
        try {
            Session s = this.factory.getObject().getCurrentSession();
            String queryString = "(Select * "
                    + "From user_permission Where userId=?1 and permissionId=?2 "
                    + "Limit 1)";
            Query query = s.createNativeQuery(queryString, UserPermission.class);
            query.setParameter(1, userId);
            query.setParameter(2, permissionId);
            return (UserPermission) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
