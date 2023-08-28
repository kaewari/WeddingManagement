/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.qltc.repository;

import com.qltc.pojo.User;
import java.util.List;

/**
 *
 * @author sonho
 */
public interface UserRepository {

    User getUserByName(String name);

    User getUserById(Integer id);

    List<User> getUsers();

    Boolean deleteUserById(Integer id);

    Boolean updateUser(User u);

    boolean authUser(String name, String password);

    User addUser(User user);
}
