/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qltc.service.impl;

import com.qltc.pojo.User;
import com.qltc.pojo.UserPermission;
import com.qltc.repository.UserPermissionRepository;
import com.qltc.repository.UserRepository;
import com.qltc.service.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepo.getUserByName(username);
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
