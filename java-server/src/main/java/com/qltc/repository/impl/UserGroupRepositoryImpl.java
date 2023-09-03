package com.qltc.repository.impl;

import com.qltc.pojo.Permission;
import com.qltc.pojo.User;
import com.qltc.pojo.UserGroup;
import com.qltc.pojo.UserGroupPermission;
import com.qltc.pojo.UserInGroup;
import com.qltc.repository.UserGroupRepository;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserGroupRepositoryImpl implements UserGroupRepository {

    @Autowired
    LocalSessionFactoryBean sessionFactory;

    @Override
    public List<UserGroup> findAll() {
        Session session = sessionFactory.getObject().getCurrentSession();
        Query query = session.createQuery("From UserGroup");
        return query.getResultList();
    }

    @Override
    public UserGroup findById(int id) {
        Session session = sessionFactory.getObject().getCurrentSession();
        return session.get(UserGroup.class, id);
    }

    @Override
    public List<User> getAllUsersOfUserGroup(UserGroup userGroup) {
        Session session = sessionFactory.getObject().getCurrentSession();
        Query query = session.createQuery("Select u From UserGroup ug Join UserInGroup uig On ug.id = uig.group Join User u On uig.permission = u.id Where ug.id = ?1");
        query.setParameter(1, userGroup.getId());
        return query.getResultList();
    }

    @Override
    public List<Permission> getAllPermissionsOfUserGroup(UserGroup userGroup) {
        Session session = sessionFactory.getObject().getCurrentSession();
        Query query = session.createQuery("Select p From UserGroup ug Join UserGroupPermission ugp On ug.id = ugp.group Join Permission p On ugp.permission = p.id Where ug.id = ?1");
        query.setParameter(1, userGroup.getId());
        return query.getResultList();
    }

    @Override
    public List<Permission> getAllPermissionsOfUserGroup(UserGroup userGroup, boolean allows) {
        Session session = sessionFactory.getObject().getCurrentSession();
        Query query = session.createQuery("From UserGroupPermission ugp Where ugp.groupId = ?");
        query.setParameter(0, userGroup.getId());
        List<UserGroupPermission> userGroupPermissionList = query.getResultList();
        if (!userGroupPermissionList.isEmpty()) {
            List<Permission> permissions = new LinkedList<>();
            userGroupPermissionList.forEach((UserGroupPermission userGroupPermission) -> {
                if (userGroupPermission.getAllows() == allows) {
                    permissions.add(userGroupPermission.getPermission());
                }
            });
            return permissions;
        }
        return null;
    }

    @Override
    public boolean addOrUpdateUserGroup(UserGroup userGroup) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            if (userGroup.getId() == null) { //adding
                session.save(userGroup);
            } else { //updating
                session.update(userGroup);
            }
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean deleteUserGroupById(int id) {
        try {
            UserGroup existing = findById(id);
            if (existing != null) {
                Session session = sessionFactory.getObject().getCurrentSession();
                session.delete(existing);
            }
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean deleteUserGroup(UserGroup userGroup) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            session.delete(userGroup);
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean addUserToGroup(User user, UserGroup userGroup) {
        UserInGroup userInGroup = getUserExistingInGroup(user, userGroup);
        if (userInGroup != null) {
            return false;
        }
        try {
            Session session = sessionFactory.getObject().getCurrentSession();
            UserInGroup adding = new UserInGroup();
            adding.setUser(user);
            adding.setUserGroup(userGroup);
            session.save(adding);
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    @Override
    public boolean removeUserFromGroup(User user, UserGroup userGroup) {
        UserInGroup userInGroup = getUserExistingInGroup(user, userGroup);
        if (userInGroup == null) {
            return false;
        }
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            session.delete(userInGroup);
            return true;
        } catch (HibernateException e) {
            return false;
        }
    }

    private UserInGroup getUserExistingInGroup(User user, UserGroup userGroup) {
        Session session = sessionFactory.getObject().getCurrentSession();
        try {
            Query query = session.createQuery("From UserInGroup uig Where"
                    + " uig.userId = ? and uig.groupId = ?");
            query.setParameter(0, user.getId());
            query.setParameter(1, userGroup.getId());
            query.setMaxResults(1);
            UserInGroup existing = (UserInGroup) query.getResultList().get(0);
            return existing;
        } catch (Exception e) {
            return null;
        }
    }

}
