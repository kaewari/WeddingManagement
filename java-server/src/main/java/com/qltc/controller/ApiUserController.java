package com.qltc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qltc.components.JwtService;
import com.qltc.pojo.Permission;
import com.qltc.pojo.User;
import com.qltc.pojo.UserGroup;
import com.qltc.pojo.UserGroupPermission;
import com.qltc.pojo.UserPermission;
import com.qltc.service.PermissionService;
import com.qltc.service.UserGroupService;
import com.qltc.service.UserPermissionService;
import com.qltc.service.UserService;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.cloudinary.json.JSONException;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ApiUserController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder passEncoder;
    @Autowired
    private JSONObject message;
    @Autowired
    private UserPermissionService userPermissionService;
    @Autowired
    private UserGroupService userGroupService;
    @Autowired
    private PermissionService permissionService;

    @PostMapping(path = "/login/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<Object> login(@RequestBody User user) throws JsonProcessingException {
        try {
            if (!this.userService.authUser(user.getName(), user.getPassword())) {
                return new ResponseEntity<>("Wrong name or password", HttpStatus.BAD_REQUEST);
            } else {
                String token = this.jwtService.generateTokenLogin(user.getName());
                User userInfo = userService.getUserByName(user.getName());
                String role = "Customer";
                if (userInfo.getEmployee() != null) {
                    role = userInfo.getEmployee().getPosition();
                }
                List<String> permissions = userService.getPermissions(userInfo.getId());
                JSONObject resToken = new JSONObject();
                resToken.put("role", role);
                resToken.put("permissions", permissions);
                resToken.put("access_token", token);
                return new ResponseEntity<>(resToken.toString(), HttpStatus.OK);
            }
        } catch (JSONException e) {
            message.put("Msg", "Cannot login");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get_authorities")
    @ResponseStatus(HttpStatus.OK)
    public Set getAuthorities(Principal principal) {
        if (principal == null) {
            return new HashSet();
        }
        String name = principal.getName();
        User user = userService.getUserByName(name);
        List<String> userPermission = userService.getPermissions(user.getId());
        Set<GrantedAuthority> authorities = new HashSet<>();
        userPermission.forEach(u -> {
            authorities.add(new SimpleGrantedAuthority(u));
        });
        return authorities;
    }

    @RequestMapping("/test/{id}")
    @CrossOrigin
    @Transactional
    public ResponseEntity<List<Permission>> test(@PathVariable int id, Principal principal) {
        return new ResponseEntity<>(this.userPermissionService.getPermissionsOfUserByUserId(id), HttpStatus.OK);
    }

    @PostMapping(path = "/user/",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<Object> addUser(@ModelAttribute User u, @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            if (this.userService.findUserInfo("name", u.getName())) {
                message.put("Msg", "This name existed");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
            }
            if (this.userService.findUserInfo("email", u.getEmail())) {
                message.put("Msg", "This email existed");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
            }
            if (this.userService.findUserInfo("phone", u.getPhone())) {
                message.put("Msg", "This phone number existed");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
            }
            if (this.userService.findUserInfo("identityNumber", u.getIdentityNumber())) {
                message.put("Msg", "This identity number existed");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
            }
            User user = new User();
            user.setAddress(u.getAddress());
            user.setPhone(u.getPhone());
            user.setEmail(u.getEmail());
            user.setName(u.getName());
            user.setPassword(this.passEncoder.encode(u.getPassword()));
            user.setIdentityNumber(u.getIdentityNumber());
            user.setIsActive(false);
            user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            User isUser = this.userService.addUser(user, file);
            if (isUser != null) {
//                message.put("Msg", "Create user successfully");
                UserGroup ug = this.userGroupService.findByName("USER");
                if (this.userGroupService.addUserToGroup(this.userService.getUserById(isUser.getId()), ug)) {
                    List<UserGroupPermission> userGroupPermissions = this.userGroupService.findByGroupId(ug.getId());
                    userGroupPermissions.forEach(ugp -> {
                        UserPermission userPermission = new UserPermission();
                        userPermission.setUser(isUser);
                        userPermission.setPermission(this.permissionService.findById(ugp.getPermission().getId()));
                        userPermission.setAllow(Boolean.TRUE);
                        this.userPermissionService.addUserPermission(userPermission);
                    });
                }
                return new ResponseEntity<>(isUser, HttpStatus.CREATED);
            } else {
                message.put("Msg", "Create user failure");
                return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);

            }
        } catch (JSONException e) {
            message.put("Msg", "Cannot create user");
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/users/update/{id}/",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<Object> updateUser(@PathVariable int id, @ModelAttribute User u, @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            User user = this.userService.getUserById(id);
            if (user != null) {
                if (file != null) {
                    user.setFile(file);
                }
                if (u.getAddress() != null) {
                    user.setAddress(u.getAddress());
                }
                if (u.getPhone() != null) {
                    user.setPhone(u.getPhone());
                }
                if (u.getEmail() != null) {
                    user.setEmail(u.getEmail());
                }
                if (u.getName() != null) {
                    user.setName(u.getName());
                }
                if (u.getPassword() != null) {
                    user.setPassword(this.passEncoder.encode(u.getPassword()));
                }
                if (u.getIdentityNumber() != null) {
                    user.setIdentityNumber(u.getIdentityNumber());
                }
                if (this.userService.updateUser(user)) {
//                    message.put("Msg", "Update user successfully");
                    return new ResponseEntity<>(this.userService.getUserById(id), HttpStatus.OK);
                } else {
                    message.put("Msg", "Update user Failure");
                    return new ResponseEntity<>(message.toString(), HttpStatus.CONFLICT);
                }

            } else {
                message.put("Msg", "Cannot find this user");
                return new ResponseEntity<>(message.toString(), HttpStatus.NOT_FOUND);
            }
        } catch (JSONException e) {
            message.put("Msg", "Cannot update this user");
            return new ResponseEntity<>(message.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/current-user/", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<Object> getCurrentUser(Principal pricipal) {
        try {
            User user = this.userService.getUserByName(pricipal.getName());
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            message.put("Msg", "This user does not exist");
            return new ResponseEntity<>(message.toString(), HttpStatus.NOT_FOUND);
        } catch (JSONException e) {
            message.put("Msg", "Cannot get current user");
            return new ResponseEntity<>(message.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/users/")
    @CrossOrigin
    public ResponseEntity<Object> getUsers() {
        try {
            List<User> users = this.userService.getUsers();
            if (users != null) {
                return new ResponseEntity<>(users, HttpStatus.OK);
            }
            message.put("Msg", "Don't have any users");
            return new ResponseEntity<>(message.toString(), HttpStatus.NOT_FOUND);
        } catch (JSONException e) {
            message.put("Msg", "Cannot get users");
            return new ResponseEntity<>(message.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/users/name/{name}/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<Object> getUserByName(@PathVariable(value = "name") String name) {
        try {
            User user = this.userService.getUserByName(name);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            message.put("Msg", "This user does not exist");
            return new ResponseEntity<>(message.toString(), HttpStatus.NOT_FOUND);
        } catch (JSONException e) {
            message.put("Msg", "Cannot get this user");
            return new ResponseEntity<>(message.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/users/id/{id}/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<Object> getUserById(@PathVariable(value = "id") int id) {
        try {
            User user = this.userService.getUserById(id);
            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
            message.put("Msg", "This user doesn not exist");
            return new ResponseEntity<>(message.toString(), HttpStatus.NOT_FOUND);
        } catch (JSONException e) {
            message.put("Msg", "Cannot get this user");
            return new ResponseEntity<>(message.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/users/delete/{id}/", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    public ResponseEntity<String> deleteUserById(@PathVariable(value = "id") int id) {
        try {
            if (this.userService.deleteUserById(id)) {
                return new ResponseEntity<>("Delete Successfully", HttpStatus.OK);
            }
            message.put("Msg", "This user does not exist");
            return new ResponseEntity<>(message.toString(), HttpStatus.NOT_FOUND);
        } catch (JSONException e) {
            return new ResponseEntity<>("Delete Failure", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{userId}/grant-permission")
    @CrossOrigin
    // {"allows": "Boolean", "permissionIds": "Array[]"}
    public ResponseEntity grantPermssionForUser(@PathVariable("userId") int id,
            @RequestBody Map<String, Object> request) {
        User existing = userService.getUserById(id);
        if (existing == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        Boolean isGranted = (Boolean) request.get("allows");
        if (isGranted == null) {
            isGranted = true;
        }
        List<Integer> permissionIds = (List<Integer>) request.get("permissionIds");
        List<Permission> permissions = new LinkedList<>();
        for (Integer pId : permissionIds) {
            Permission permission = permissionService.findById(pId);
            if (permission == null) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }
            permissions.add(permission);
        }

        if (isGranted && permissionService.grantPermissionForUser(existing, permissions)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else if (!isGranted && permissionService.denyPermissionForUser(existing, permissions)) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{userId}/reset-permission")
    @CrossOrigin
    public ResponseEntity resetPermission(@PathVariable("userId") int id) {
        User existing = userService.getUserById(id);
        if (existing != null && permissionService.resetAllPermissionsOfUser(existing)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
