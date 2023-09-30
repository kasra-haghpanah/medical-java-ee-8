package com.kasra.javaee.service;

import com.kasra.javaee.interceptor.Logged;
import com.kasra.javaee.interfaces.repository.ISickRepository;
import com.kasra.javaee.interfaces.repository.IUserRepository;
import com.kasra.javaee.model.Sick;
import com.kasra.javaee.model.User;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

/**
 * Created by kasra.haghpanah on 02/09/2016.
 */
@Named
@RequestScoped
public class UserService implements IUserRepository {

    @EJB
    IUserRepository userRepository;


    @Logged
    public User save(User entity) {
        return userRepository.save(entity);
    }

    @Logged
    public void delete(User entity) {
        userRepository.delete(entity);
    }

    @Logged
    public void update(User entity) {
        userRepository.update(entity);
    }


    public List<User> getAll() {
        return userRepository.getAll();
    }


    public User getById(int id) {
        return userRepository.getById(id);
    }


    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }


}
