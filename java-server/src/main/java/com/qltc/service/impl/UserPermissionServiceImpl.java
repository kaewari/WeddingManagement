/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.service.impl;

import com.qltc.pojo.Permission;
import com.qltc.pojo.UserPermission;
import com.qltc.repository.UserPermissionRepository;
import com.qltc.service.UserPermissionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author sonho
 */
@Service
public class UserPermissionServiceImpl implements UserPermissionService {

    @Autowired
    private UserPermissionRepository permissionRepository;

    @Override
    public UserPermission getUserPermissionById(int id) {
        return this.permissionRepository.getUserPermissionById(id);
    }

    @Override
    public List<Permission> getPermissionsOfUserByUserId(int userId) {
        return this.permissionRepository.getPermissionsOfUserByUserId(userId);
    }

    @Override
    public UserPermission addUserPermissionsByUserId(UserPermission userPermisison) {
        return this.permissionRepository.addUserPermissionsByUserId(userPermisison);
    }

    @Override
    public boolean updateUserPermissionById(UserPermission userPermisison) {
        return this.permissionRepository.updateUserPermissionById(userPermisison);
    }

    @Override
    public boolean deleteUserPermissionById(int id) {
        return this.permissionRepository.deleteUserPermissionById(id);
    }

    @Override
    public boolean deleteUserPermissionsByUserId(int userId) {
        return this.permissionRepository.deleteUserPermissionsByUserId(userId);
    }

    @Override
    public boolean deleteUserPermissionsByPermissionId(int permissionId) {
        return this.permissionRepository.deleteUserPermissionsByPermissionId(permissionId);
    }

    @Override
    public UserPermission checkExistUserPermission(int userId, int permissionId) {
        return this.permissionRepository.checkExistUserPermission(userId, permissionId);
    }

    @Override
    public List<UserPermission> getUserPermissionsByUserId(int userId) {
        return this.permissionRepository.getUserPermissionsByUserId(userId);
    }

    @Override
    public List<UserPermission> getUserPermissionsByPermissionId(int permissionId) {
        return this.permissionRepository.getUserPermissionsByPermissionId(permissionId);
    }

}
