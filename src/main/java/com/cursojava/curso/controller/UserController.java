package com.cursojava.curso.controller;

import antlr.Token;
import com.cursojava.curso.dao.UserDAO;
import com.cursojava.curso.models.User;
import com.cursojava.curso.util.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private JWTUtil jwtUtil;
    @RequestMapping(value="proof")
    public List<String> proof(){
        return List.of("apple", "kiwi", "banana") ;
    }

    //This is a method with harcode
    @RequestMapping(value="user1")
    public User getUserHarcode(){
        User user = new User();
        user.setName("Kylian");
        user.setLastName("Mbappé");
        user.setEmail("kilian@hotmail.com");
        user.setTelephone("9513626076");
        user.setPassword("Nopasswordavailable");
        return user;
    }
    //@RequestMapping(value="api/user/{id}") This can be replace by what's down:
    @RequestMapping(value="api/users/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id){
        User user = new User();
        user.setId(id);
        user.setName("Kylian");
        user.setLastName("Mbappé");
        user.setEmail("kilian@hotmail.com");
        user.setTelephone("9513626076");
        user.setPassword("Nopasswordavailable");
        return user;
    }

    /*This method is harcoded
    @RequestMapping(value="users")
    public List<User> getUsers(){

        List<User> users = new ArrayList<>();

        User user1 = new User();
        user1.setId(1L);
        user1.setName("Kylian");
        user1.setLastName("Mbbapé");
        user1.setEmail("kilian@hotmail.com");
        user1.setTelephone("9513626076");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("Lionel");
        user2.setLastName("Messi");
        user2.setEmail("messi@hotmail.com");
        user2.setTelephone("9527625076");

        User user3 = new User();
        user3.setId(3L);
        user3.setName("Paul");
        user3.setLastName("Pogba");
        user3.setEmail("paulpogba@hotmail.com");
        user3.setTelephone("5513706091");

        users.add(user1);
        users.add(user2);
        users.add(user3);

        return users;
    }*/
    //Si no se declara método, por defecto será un GET
    @RequestMapping(value="api/users")
    public List<User> getUsers(@RequestHeader(value = "Autorization") String token) {
        if (!verifyToken(token)){
            return null;
        }
        return userDAO.getUsers();
    }

    private boolean verifyToken(String token){
        String userId = jwtUtil.getKey(token);
        return userId != null;
    }
    
    @RequestMapping(value="api/users", method = RequestMethod.POST)
    public void registerUser(@RequestBody User user) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1,1024,1,user.getPassword());
        user.setPassword(hash);
        userDAO.register(user);
    }
    @RequestMapping(value="user3")
    public User editUser(){
        User user = new User();
        user.setName("Kylian");
        user.setLastName("Mbappé");
        user.setEmail("kilian@hotmail.com");
        user.setTelephone("9513626076");
        return user;
    }
    //@RequestMapping(value="user4")
    @RequestMapping(value="api/users/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@RequestHeader(value = "Autorization") String token,
                           @PathVariable Long id){
        if (!verifyToken(token)){
            return;
        }
        userDAO.delete(id);
    }
}
