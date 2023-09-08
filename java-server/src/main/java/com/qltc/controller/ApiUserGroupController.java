package com.qltc.controller;

import com.qltc.pojo.Permission;
import com.qltc.pojo.User;
import com.qltc.pojo.UserGroup;
import com.qltc.service.PermissionService;
import com.qltc.service.UserGroupService;
import com.qltc.service.UserService;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-group")
public class ApiUserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private PermissionService permissionService;
    
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('VIEW_USER_GROUP')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserGroup> getAll() {
        return userGroupService.findAll();
    }

    @PreAuthorize("hasAuthority('VIEW_USER_GROUP')")
    @GetMapping("/{userGroupId}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable("userGroupId") int id) {
        UserGroup existing = userGroupService.findById(id);
        if (existing != null) {
               List<User> users = userGroupService.getAllUsersOfUserGroup(existing);
            List<Permission> permissions = userGroupService.getAllPermissionsOfUserGroup(existing);
            List<Map<String, Object>> pms = new LinkedList<>();
            for (Permission p : permissions) {

                boolean allows = permissionService.getUserGroupPermissionState(existing, p);

                pms.add(new HashMap<String, Object>() {
                    {
                        put("id", p.getId());
                        put("value", p.getValue());
                        put("allow", allows);
                    }
                });
            }

            Map<String, Object> response = new HashMap<String, Object>() {
            };
            response.put("id", existing.getId());
            response.put("name", existing.getName());
            response.put("users", users);
            response.put("permissions", pms);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ADD_NEW_USER_GROUP')")
    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED) //ok
    public ResponseEntity<UserGroup> addNewUserGroup(@ModelAttribute UserGroup userGroup) {
        if (userGroupService.addUserGroup(userGroup)) {
            return new ResponseEntity<>(userGroup, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_USER_GROUP')")
    @PutMapping("/{userGroupId}")
    public ResponseEntity updateExistingUserGroup(@PathVariable("userGroupId") int id,
            @RequestBody UserGroup userGroup) {
        if (userGroup.getId()!= null && userGroup.getId() == id && userGroupService.updateUserGroup(userGroup)) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_USER_GROUP')")
    @PostMapping(path = "/{userGroupId}/add-user")
    public ResponseEntity addUserToGroup(@PathVariable("userGroupId") int id,
            @RequestParam("userId") int userId) {
        UserGroup existing = userGroupService.findById(id);
        if (existing != null) {
            User user = userService.getUserById(userId);
            if (user != null && userGroupService.addUserToGroup(user, existing)) {
                return new ResponseEntity(HttpStatus.CREATED);
            } else {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }   

    @PreAuthorize("hasAuthority('MODIFY_EXISTING_USER_GROUP')")
    @PostMapping(path = "/{userGroupId}/remove-user")
    public ResponseEntity removeUserFromGroup(@PathVariable("userGroupId") int id,
            @RequestParam("userId") int userId) {
        UserGroup existing = userGroupService.findById(id);
        if (existing != null) {
            User user = userService.getUserById(userId);
            if (user != null && userGroupService.removeUserFromGroup(user, existing)) {
                return new ResponseEntity(HttpStatus.CREATED);
            } else {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }   
    
    @PreAuthorize("hasAuthority('MODIFY_EXISTING_USER_GROUP')")
    @PostMapping("/{userGroupId}/grant-permission")
    //{"allows" : "boolean" , "permissionIds" : [1, 2, 3, 7]}
    public ResponseEntity grantPermissionForUserGroup(@PathVariable("userGroupId") int id,
            @RequestBody Map<String, Object> request) {
        UserGroup userGroup = userGroupService.findById(id);
        if (userGroup == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        List<Permission> permissions = new LinkedList<>();
        Boolean isGranted = (Boolean) request.get("allows");
        List<Integer> permissionIds = (List<Integer>) request.get("permissionIds");
        for (Integer eachId : permissionIds) {
            Permission permission = permissionService.findById(eachId);
            if (permission == null) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            permissions.add(permission);
        }

        if (isGranted && permissionService.grantPermissionForUserGroup(userGroup, permissions)) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else if (!isGranted && permissionService.denyPermissionForUserGroup(userGroup, permissions)) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PreAuthorize("hasAuthority('MODIFY_EXISTING_USER_GROUP')")
    @DeleteMapping("/{userGroupId}/reset-permission")
    public ResponseEntity resetAllPermissionOfUserGroup(@PathVariable("userGroupId") int id) {
        UserGroup existing = userGroupService.findById(id);
        if (existing != null && permissionService.resetAllPermissionsOfUserGroup(existing)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    //not usually use
    @PreAuthorize("hasAuthority('DELETE_EXISTING_USER_GROUP')")
    @DeleteMapping("/{userGroupId}")
    // only when immediately created
    public ResponseEntity deleteUserGroup(@PathVariable("userGroupId") int id) {
        if (userGroupService.deleteUserGroupById(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
