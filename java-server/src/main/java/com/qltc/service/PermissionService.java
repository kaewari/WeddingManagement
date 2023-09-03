package com.qltc.service;

import com.qltc.pojo.Permission;
import com.qltc.pojo.User;
import com.qltc.pojo.UserGroup;
import java.util.List;

public interface PermissionService {

    public List<Permission> findAll();

    public Permission findById(int id);

    public List<Permission> findByName(String name);

    public boolean getUserPermissionState(User user, Permission permission);

    public boolean getUserGroupPermissionState(UserGroup userGroup, Permission permission);

    public boolean resetAllPermissionsOfUser(User user) throws NoSuchFieldException;

    public boolean resetAllPermissionsOfUserGroup(UserGroup userGroup) throws NoSuchFieldException;

    public boolean grantPermissionForUserGroup(UserGroup userGroup, List<Permission> permissions);

    public boolean denyPermissionForUserGroup(UserGroup userGroup, List<Permission> permissions);

    public boolean grantPermissionForUser(User user, List<Permission> permissions);

    public boolean denyPermissionForUser(User user, List<Permission> permissions);
}
