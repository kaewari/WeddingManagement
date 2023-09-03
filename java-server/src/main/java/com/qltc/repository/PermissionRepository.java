package com.qltc.repository;

import com.qltc.pojo.Permission;
import com.qltc.pojo.User;
import com.qltc.pojo.UserGroup;
import java.util.List;

public interface PermissionRepository {

    public List<Permission> findAll();

    public Permission findById(int id);

    public List<Permission> findByName(String permission);

    public boolean getUserPermissionState(User user, Permission permission);

    public boolean getUserGroupPermissionState(UserGroup userGroup, Permission permission);

    public boolean grantOrDenyPermissionForUser(User user, List<Permission> permissions,
             boolean allows);

    public boolean grantOrDenyPermissionForGroupUser(UserGroup userGroup,
             List<Permission> permissions, boolean allows);

    public boolean resetPermisisonOfUser(User user);

    public boolean resetPermissionOfUserGroup(UserGroup userGroup);
}
