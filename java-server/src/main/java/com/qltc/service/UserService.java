package com.qltc.service;

import com.qltc.pojo.User;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author sonho
 * @param <T>
 * @param <V>
 */
public interface UserService<T, V> extends UserDetailsService {

    User getUserByName(String name);

    boolean findUserInfo(T key, V value);

    User getUserById(int id);

    List<User> getUsers();

    boolean deleteUserById(int id);

    boolean updateUser(User u);

    boolean authUser(String name, String password);

    User addUser(User user, MultipartFile file);
}
