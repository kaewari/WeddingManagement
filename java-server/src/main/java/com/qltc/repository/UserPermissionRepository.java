/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.qltc.repository;

import com.qltc.pojo.Permission;
import java.util.List;

/**
 *
 * @author sonho
 */
public interface UserPermissionRepository {

    Permission getPermissionById(int id);

    List<Permission> getPermissionsByUserId(int userId);

    Boolean addOrUpdatePermissionsByUserId();

    Boolean deleteUserPermissionById(int id);

    Boolean deleteUserPermissionsByUserId(int userId);

    Boolean deleteUserPermissionsByPermissionId(int permissionId);
}
