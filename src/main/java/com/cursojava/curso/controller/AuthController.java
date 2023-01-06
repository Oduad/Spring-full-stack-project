package com.cursojava.curso.controller;

import com.cursojava.curso.dao.UserDAO;
import com.cursojava.curso.models.User;
import com.cursojava.curso.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value="api/login", method = RequestMethod.POST)
    public String login(@RequestBody User user) {

        User logeduser = userDAO.getUserByCredentials(user);

        if(logeduser!=null){
            String tokenJwt = jwtUtil.create(String.valueOf(logeduser.getId()),logeduser.getEmail());
            return tokenJwt;
        }return "FAIL";
    }
}
