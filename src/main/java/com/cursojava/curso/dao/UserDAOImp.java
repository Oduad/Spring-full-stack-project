package com.cursojava.curso.dao;

import com.cursojava.curso.models.User;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Repository
@Transactional
public class UserDAOImp implements UserDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<User> getUsers() {
        String query = "FROM User";
        List<User> result = entityManager.createQuery(query).getResultList();
        return result;
    }

    @Override
    public void delete(Long id) {
        User user = entityManager.find(User.class,id);
        entityManager.remove(user);
    }

    @Override
    public void register(User user) {
        entityManager.merge(user);
    }

    @Override
    //public boolean verifyCredentials(User user) {
    public User getUserByCredentials(User user) {
        //String query = "FROM User WHERE email = :email AND password = :password ";
        String query = "FROM User WHERE email = :email";
        List<User> list = entityManager.createQuery(query)
                .setParameter("email",user.getEmail())
                //.setParameter("password",user.getPassword())
                .getResultList();

        //This is for avoiding a null Pointer Exception
        if(list.isEmpty()){
            return null;
        }

        //Esto se agregó al final para incio de sesión
        String passwordHashed = list.get(0).getPassword();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

         /*if(list.isEmpty()){
            return false;
        }else{
            return true;
        }*/

        //Lo anterior se puede resumir en
        //return !list.isEmpty();

        //return argon2.verify(passwordHashed,user.getPassword());

        if (argon2.verify(passwordHashed,user.getPassword())){
            return list.get(0);
        }
        return null;
    }
}
