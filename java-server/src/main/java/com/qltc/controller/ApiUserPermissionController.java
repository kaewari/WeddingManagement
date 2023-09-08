package com.qltc.controller;

import com.qltc.pojo.UserPermission;
import com.qltc.service.PermissionService;
import com.qltc.service.UserPermissionService;
import com.qltc.service.UserService;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import org.cloudinary.json.JSONException;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author sonho
 */
@RestController
@RequestMapping("/api")
public class ApiUserPermissionController {

    @Autowired
    private UserPermissionService userPermissionService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private JSONObject message;
    @Autowired
    private UserService userService;

    @GetMapping(path = "/user-permission/id/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> getUserPermissionById(@PathVariable int id) {
        return new ResponseEntity<>(this.userPermissionService.getUserPermissionById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/user-permission/user-id/{userId}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> getPermissionsOfUserByUserId(@PathVariable int userId) {
        return new ResponseEntity<>(this.userPermissionService.getPermissionsOfUserByUserId(userId), HttpStatus.OK);
    }

    @PostMapping(path = "/user-permission/add/",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> addUserPermissionByUserId(@RequestBody Map<String, String> userPermisison) {
        try {
            if (this.userPermissionService.checkExistUserPermission(Integer.parseInt(userPermisison.get("userId")),
                    Integer.parseInt(userPermisison.get("permissionId"))) != null) {
                message.put("Msg", "User has this permission");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
            }
            UserPermission uPermission = new UserPermission();
            uPermission.setPermission(this.permissionService.findById(Integer.parseInt(userPermisison.get("permissionId"))));
            uPermission.setUser(this.userService.getUserById(Integer.parseInt(userPermisison.get("userId"))));
            uPermission.setAllow(Boolean.TRUE);
            return new ResponseEntity<>(this.userPermissionService.addUserPermissionsByUserId(uPermission), HttpStatus.OK);
        } catch (NumberFormatException e) {
            message.put("Msg", "Cannot add new user permission");
            return new ResponseEntity<>(message.toString(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping(path = "/user-permission/update/{id}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> updateUserPermissionById(@PathVariable int id, @RequestBody Map<String, String> userPermisison) {
        try {
            UserPermission uPermission = this.userPermissionService.getUserPermissionById(id);
            if (uPermission != null) {
                uPermission.setUser(this.userService.getUserById(Integer.parseInt(userPermisison.get("userId"))));
                uPermission.setPermission(this.permissionService.findById(Integer.parseInt(userPermisison.get("permissionId"))));
                uPermission.setAllow(Boolean.valueOf(userPermisison.get("allow")));
                if (this.userPermissionService.updateUserPermissionById(uPermission)) {
                    message.put("Msg", "Update successfully");
                    return new ResponseEntity<>(message.toString(), HttpStatus.OK);
                } else {
                    message.put("Msg", "Update failure");
                    return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
                }
            } else {
                message.put("Msg", "This id does not exist");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
            }
        } catch (NumberFormatException | JSONException e) {
            message.put("Msg", "Cannot update this user permission");
            return new ResponseEntity<>(message.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/user-permission/delete/id/{id}")
    public ResponseEntity<Object> deleteUserPermissionById(@PathVariable int id) {
        try {
            if (this.userPermissionService.deleteUserPermissionById(id)) {
                message.put("Msg", "Delete successfully");
                return new ResponseEntity<>(message.toString(), HttpStatus.OK);
            } else {
                message.put("Msg", "Delete failure");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
            }
        } catch (JSONException e) {
            message.put("Msg", "Cannot delete this user permission");
            return new ResponseEntity<>(message.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/user-permission/delete/user-id/{userId}")
    public ResponseEntity<Object> deleteUserPermissionsByUserId(@PathVariable int userId) {
        try {
            if (this.userPermissionService.deleteUserPermissionsByUserId(userId)) {
                message.put("Msg", "Delete successfully");
                return new ResponseEntity<>(message.toString(), HttpStatus.OK);
            } else {
                message.put("Msg", "Delete failure");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
            }
        } catch (JSONException e) {
            message.put("Msg", "Cannot delete this user permission");
            return new ResponseEntity<>(message.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/user-permission/delete/permission-id/{permissionId}")
    public ResponseEntity<Object> deleteUserPermissionsByPermissionId(@PathVariable int permissionId) {
        try {
            if (this.userPermissionService.deleteUserPermissionsByPermissionId(permissionId)) {
                message.put("Msg", "Delete successfully");
                return new ResponseEntity<>(message.toString(), HttpStatus.OK);
            } else {
                message.put("Msg", "Delete failure");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
            }
        } catch (JSONException e) {
            message.put("Msg", "Cannot delete this user permission");
            return new ResponseEntity<>(message.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/test/id/{id}")
    @CrossOrigin
    public ResponseEntity<List<UserPermission>> test(@PathVariable int id, Principal principal) {
        return new ResponseEntity<>(this.userPermissionService.getUserPermissionsByUserId(id), HttpStatus.OK);
    }

}
