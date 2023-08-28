/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.qltc.pojo.User;
import com.qltc.pojo.UserPermission;
import com.qltc.repository.UserPermissionRepository;
import com.qltc.repository.UserRepository;
import com.qltc.service.UserService;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author sonho
 */
@Service("userDetailsService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserPermissionRepository userPermissionRepo;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public User getUserById(Integer id) {
        return this.userRepo.getUserById(id);
    }

    @Override
    public List<User> getUsers() {
        return this.userRepo.getUsers();
    }

    @Override
    public Boolean deleteUserById(Integer id) {
        return this.userRepo.deleteUserById(id);
    }

    @Override
    public Boolean updateUser(User u) {
        if (!u.getFile().isEmpty()) {
            try {
                Map res = this.cloudinary.uploader().upload(u.getFile().getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                u.setAvatar(res.get("secure_url").toString());
            } catch (IOException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return this.userRepo.updateUser(u);
    }

    @Override
    public boolean authUser(String name, String password) {
        return this.userRepo.authUser(name, password);
    }

    @Override
    public User addUser(Map<String, String> params, MultipartFile avatar) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User getUserByName(String name) {
        return this.userRepo.getUserByName(name);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = this.userRepo.getUserByName(name);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid user!");
        }
        List<UserPermission> userPermission = (List<UserPermission>) (UserPermission) this.userPermissionRepo.getPermissionsByUserId(user.getId());
        Set<GrantedAuthority> authorities = new HashSet<>();
        userPermission.forEach(u -> {
            authorities.add(new SimpleGrantedAuthority(u.toString()));

        });
        return new org.springframework.security.core.userdetails.User(
                user.getName(), user.getPassword(), authorities);
    }

}
