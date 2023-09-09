package com.qltc.service.impl;

import com.qltc.pojo.Permission;
import com.qltc.pojo.User;
import com.qltc.pojo.UserGroup;
import com.qltc.repository.UserGroupRepository;
import com.qltc.service.UserGroupService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserGroupServiceImpl implements UserGroupService {

    @Autowired
    private UserGroupRepository userGroupRepo;

    @Override
    public List<UserGroup> findAll() {
        return userGroupRepo.findAll();
    }

    @Override
    public UserGroup findById(int id) {
        return userGroupRepo.findById(id);
    }

    @Override
    public List<User> getAllUsersOfUserGroup(UserGroup userGroup) {
        return userGroupRepo.getAllUsersOfUserGroup(userGroup);
    }

    @Override
    public List<Permission> getAllPermissionsOfUserGroup(UserGroup userGroup) {
        return userGroupRepo.getAllPermissionsOfUserGroup(userGroup);
    }

    @Override
    public List<Permission> getAllowedPermissionsOfUserGroup(UserGroup userGroup, boolean allows) {
        return userGroupRepo.getAllPermissionsOfUserGroup(userGroup, true);
    }

    @Override
    public List<Permission> getDeniedPermissionsOfUserGroup(UserGroup userGroup) {
        return userGroupRepo.getAllPermissionsOfUserGroup(userGroup, false);
    }

    @Override
    public boolean addUserGroup(UserGroup userGroup) {
        return userGroupRepo.addOrUpdateUserGroup(userGroup);
    }

    @Override
    public boolean updateUserGroup(UserGroup userGroup) {
        return userGroupRepo.addOrUpdateUserGroup(userGroup);
    }

    @Override
    public boolean deleteUserGroupById(int id) {
        return userGroupRepo.deleteUserGroupById(id);
    }

    @Override
    public boolean deleteUserGroup(UserGroup userGroup) {
        return userGroupRepo.deleteUserGroup(userGroup);
    }

    @Override
    public boolean addUserToGroup(User user, UserGroup userGroup) {
        return userGroupRepo.addUserToGroup(user, userGroup);
    }

    @Override
    public boolean removeUserFromGroup(User user, UserGroup userGroup) {
        return userGroupRepo.removeUserFromGroup(user, userGroup);
    }

}
