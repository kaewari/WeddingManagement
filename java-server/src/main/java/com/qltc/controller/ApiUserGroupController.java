package com.qltc.controller;

//miss addUserToGroup
import com.qltc.pojo.Permission;
import com.qltc.pojo.UserGroup;
import com.qltc.service.PermissionService;
import com.qltc.service.UserGroupService;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user-group")
public class ApiUserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping //ok
    @ResponseStatus(HttpStatus.OK)
    public List<UserGroup> getAll() {
        return userGroupService.findAll();
    }

    @GetMapping("/{userGroupId}") //ok
    public ResponseEntity<Map<String, Object>> getById(@PathVariable("userGroupId") int id) {
        UserGroup existing = userGroupService.findById(id);
        if (existing != null) {
            //List<User> users = userGroupService.getAllUsersOfUserGroup(existing);
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
            //response.put("users", users);
            response.put("permissions", pms);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping //ok
    @Transactional(propagation = Propagation.REQUIRED) //ok
    public ResponseEntity<UserGroup> addNewUserGroup(@RequestBody UserGroup userGroup) {
        if (userGroupService.addUserGroup(userGroup)) {
            return new ResponseEntity<>(userGroup, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userGroupId}") //ok
    public ResponseEntity updateExistingUserGroup(@PathVariable("userGroupId") int id,
            @RequestBody UserGroup userGroup) {
        if (userGroup.getId() == id && userGroupService.updateUserGroup(userGroup)) {
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{userGroupId}")
    public ResponseEntity addUserToGroup(@PathVariable("userGroupId") int id,
            @RequestBody Map<String, String> request) {
        UserGroup existing = userGroupService.findById(id);
        //Get user
        //add new UserInGroup
        //add that to userGroup object
        //update userGroup object
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    //remove user from group
    @PostMapping("/{userGroupId}/grant-permission") //ok
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

    @DeleteMapping("/{userGroupId}") //ok
    public ResponseEntity deleteUserGroup(@PathVariable("userGroupId") int id) {
        if (userGroupService.deleteUserGroupById(id)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
