package com.qltc.repository.impl;

import com.qltc.pojo.Permission;
import com.qltc.pojo.User;
import com.qltc.pojo.UserGroup;
import com.qltc.pojo.UserGroupPermission;
import com.qltc.pojo.UserPermission;
import com.qltc.repository.PermissionRepository;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
public class PermissionRepositoryImpl implements PermissionRepository {

    @Autowired
    private LocalSessionFactoryBean sessionFactory;

    @Override
    public List<Permission> findAll() {
        Session session = sessionFactory.getObject().getCurrentSession();
        Query query = session.createQuery("From Permission");
        return query.getResultList();
    }

    @Override
    public Permission findById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(Permission.class, id);
    }

    @Override
    public List<Permission> findByName(String permission) {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Permission> query = builder.createQuery(Permission.class);
        Root root = query.from(Permission.class);
        query.where(builder.like(builder.lower(root.get("value")),
                String.format("%%%s%%", permission.toLowerCase())));
        return session.createQuery(query).getResultList();
    }

    @Override
    public boolean getUserPermissionState(User user, Permission permission) {
        UserPermission userPermission = getUserPermission(user.getId(), permission.getId());
        return userPermission.getAllow();
    }

    @Override
    public boolean getUserGroupPermissionState(UserGroup userGroup, Permission permission) {
        UserGroupPermission userGroupPermission = getUserGroupPermission(
                userGroup.getId(), permission.getId());
        return userGroupPermission.getAllow();
    }

    @Override
    public boolean grantOrDenyPermissionForUser(User user, List<Permission> permissions,
            boolean allows) {
        Session session = sessionFactory.getObject().getCurrentSession();
        if (user == null || permissions.isEmpty()) {
            return false;
        }
        for (Permission permission : permissions) {
            UserPermission existing = getUserPermission(user.getId(), permission.getId());
            if (existing != null) {
                existing.setAllow(allows);
                session.update(existing);
            } else {
                UserPermission grantOrDenyOne = new UserPermission();
                grantOrDenyOne.setUser(user);
                grantOrDenyOne.setPermission(permission);
                grantOrDenyOne.setAllow(allows);
                session.save(grantOrDenyOne);
            }
        }
        return true;
    }

    @Override
    public boolean grantOrDenyPermissionForGroupUser(UserGroup userGroup,
            List<Permission> permissions, boolean allows) {
        Session session = sessionFactory.getObject().getCurrentSession();
        if (userGroup == null || permissions.isEmpty()) {
            return false;
        }
        for (Permission permission : permissions) {
            UserGroupPermission existing = getUserGroupPermission(userGroup.getId(), permission.getId());
            if (existing != null) {
                existing.setAllow(allows);
                session.update(existing);
            } else {
                UserGroupPermission grantOrDenyOne = new UserGroupPermission();
                grantOrDenyOne.setGroup(userGroup);
                grantOrDenyOne.setPermission(permission);
                grantOrDenyOne.setAllow(allows);
                session.save(grantOrDenyOne);
            }
        }
        return true;
    }

    private UserPermission getUserPermission(int userId, int permissionId) {
        Session session = sessionFactory.getObject().getCurrentSession();
        Query query = session.createQuery("From UserPermission up Where"
                + " up.user = :userId and up.permission = :permissionId");
        query.setParameter("userId", userId);
        query.setParameter("permissionId", permissionId);
        query.setMaxResults(1);
        return (UserPermission) query.getResultList().get(0);
    }

    private UserGroupPermission getUserGroupPermission(int userGroupId, int permissionId) {
        Session session = sessionFactory.getObject().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery query = criteriaBuilder.createQuery(UserGroupPermission.class);
        Root root = query.from(UserGroupPermission.class);

        query.where(criteriaBuilder.and(
                criteriaBuilder.equal(root.get("group"), userGroupId),
                criteriaBuilder.equal(root.get("permission"), permissionId)
        ));

        List<UserGroupPermission> list = session.createQuery(query).setMaxResults(1).getResultList();
        if (!list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public boolean resetPermisisonOfUser(User user) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            Query query = session.createQuery("From UserPermission up Where"
                    + " up.userId = ?");
            query.setParameter(0, user.getId());
            List<UserPermission> userPermissions = query.getResultList();
            if (!userPermissions.isEmpty()) {
                Transaction transaction = session.beginTransaction();
                userPermissions.forEach((UserPermission userPermission) -> {
                    session.delete(userPermission);
                });
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean resetPermissionOfUserGroup(UserGroup userGroup) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            Query query = session.createQuery("From UserGroupPermission ugp Where"
                    + " ugp.groupId = ?");
            query.setParameter(0, userGroup.getId());
            List<UserGroupPermission> userGroupPermissions = query.getResultList();
            if (!userGroupPermissions.isEmpty()) {
                Transaction transaction = session.beginTransaction();
                userGroupPermissions.forEach((UserGroupPermission userGroupPermission) -> {
                    session.delete(userGroupPermission);
                });
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (HibernateException e) {
            return false;
        }
    }
}
