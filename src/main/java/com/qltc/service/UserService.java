/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.qltc.service;

import com.qltc.pojo.User;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author sonho
 */
public interface UserService extends UserDetailsService {

    User getUserById(Integer id);

    List<User> getUsers();

    Boolean deleteUserById(Integer id);

    Boolean addOrUpdateUser(User u);
}
