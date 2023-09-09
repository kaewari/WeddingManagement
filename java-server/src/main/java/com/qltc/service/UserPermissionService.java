package com.qltc.service;

import com.qltc.pojo.Permission;
import com.qltc.pojo.UserPermission;
import java.util.List;

/**
 *
 * @author sonho
 */
public interface UserPermissionService {

    List<UserPermission> getUserPermissionsByUserId(int userId);

    List<UserPermission> getUserPermissionsByPermissionId(int permissionId);

    UserPermission getUserPermissionById(int id);

    List<Permission> getPermissionsOfUserByUserId(int userId);

    UserPermission addUserPermission(UserPermission userPermisison);

    boolean updateUserPermissionById(UserPermission userPermisison);

    boolean deleteUserPermissionById(int id);

    boolean deleteUserPermissionsByUserId(int userId);

    boolean deleteUserPermissionsByPermissionId(int permissionId);

    UserPermission checkExistUserPermission(int userId, int permissionId);
}
