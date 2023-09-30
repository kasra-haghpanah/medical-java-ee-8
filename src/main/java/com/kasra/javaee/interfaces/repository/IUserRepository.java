package com.kasra.javaee.interfaces.repository;

import com.kasra.javaee.model.Sick;
import com.kasra.javaee.model.User;

import java.util.List;

/**
 * Created by kasra.haghpanah on 12/12/2016.
 */
//@Remote
public interface IUserRepository {

    public User save(User entity);

    public void delete(User entity);

    public void update(User entity);

    public List<User> getAll();

    public User getById(int id);

    public User getByUsername(String username);

}
