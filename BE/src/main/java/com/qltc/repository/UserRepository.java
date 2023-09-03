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
 * @param <T>
 * @param <V>
 */
public interface UserRepository<T, V> {

    User getUserByName(String name);

    User getUserById(int id);

    List<User> getUsers();

    boolean deleteUserById(int id);

    boolean updateUser(User u);

    boolean authUser(String name, String password);

    User addUser(User user);

    boolean findUserInfo(T key, V value);
}
