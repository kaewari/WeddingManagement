package com.qltc.repository;

import com.qltc.pojo.Permission;
import com.qltc.pojo.UserPermission;
import java.util.List;

/**
 *
 * @author sonho
 */
public interface UserPermissionRepository {

    List<UserPermission> getUserPermissionsByUserId(int userId);

    List<Permission> getPermissionsByUserId(int id);
    
    List<String> getPermissionStringsByUserId(int id);

    UserPermission getUserPermissionById(int id);

    UserPermission checkExistUserPermission(int userId, int permissionId);

    List<Permission> getPermissionsOfUserByUserId(int userId);

    UserPermission addUserPermissionsByUserId(UserPermission userPermisison);

    boolean updateUserPermissionById(UserPermission userPermisison);

    boolean deleteUserPermissionById(int id);

    boolean deleteUserPermissionsByUserId(int userId);

    boolean deleteUserPermissionsByPermissionId(int permissionId);
}
