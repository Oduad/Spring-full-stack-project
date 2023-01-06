package com.cursojava.curso.dao;
import com.cursojava.curso.models.User;
import java.util.List;

public interface UserDAO {
    List<User> getUsers();
    void delete(Long id);

    void register(User user);

    //boolean verifyCredentials(User user);

    User getUserByCredentials(User user);

}
