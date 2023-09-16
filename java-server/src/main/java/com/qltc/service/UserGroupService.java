package com.qltc.service;

import com.qltc.pojo.Permission;
import com.qltc.pojo.User;
import com.qltc.pojo.UserGroup;
import com.qltc.pojo.UserGroupPermission;
import com.qltc.pojo.UserInGroup;
import java.util.List;

public interface UserGroupService {

    public List<UserGroup> findAll();

    public UserGroup findById(int id);

    public List<User> getAllUsersOfUserGroup(UserGroup userGroup);

    public List<Permission> getAllPermissionsOfUserGroup(UserGroup userGroup);

    public List<Permission> getAllowedPermissionsOfUserGroup(UserGroup userGroup, boolean allows);

    public List<Permission> getDeniedPermissionsOfUserGroup(UserGroup userGroup);

    public boolean addUserGroup(UserGroup userGroup);

    public boolean updateUserGroup(UserGroup userGroup);

    public boolean deleteUserGroupById(int id);

    public boolean deleteUserGroup(UserGroup userGroup);

    public boolean addUserToGroup(User user, UserGroup userGroup);

    public boolean removeUserFromGroup(User user, UserGroup userGroup);
    
    //son
    public UserGroup findByName(String name);	
    public List<UserGroupPermission> findByGroupId(int groupId);	

    public UserInGroup getUserExistingInGroup(User user, UserGroup userGroup);
}
