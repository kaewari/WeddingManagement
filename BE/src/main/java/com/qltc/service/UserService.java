/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.qltc.service;

import com.qltc.pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author sonho
 */
public interface UserService extends UserDetailsService {

    User getUserByName(String name);

    User getUserById(int id);

    List<User> getUsers();

    boolean deleteUserById(int id);

    boolean updateUser(User u);

    boolean authUser(String name, String password);

    User addUser(Map<String, String> params, MultipartFile avatar);
}
