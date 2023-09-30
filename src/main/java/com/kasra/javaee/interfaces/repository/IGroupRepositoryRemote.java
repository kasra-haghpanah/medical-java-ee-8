package com.kasra.javaee.interfaces.repository;

import com.kasra.javaee.model.Group;
import com.kasra.javaee.model.User;

import java.util.List;

/**
 * Created by kasra.haghpanah on 12/12/2016.
 */
//@Remote
public interface IGroupRepositoryRemote {

    public Group save(Group entity);

    public void delete(Group entity);

    public void update(Group entity);

    public List<Group> getAll();

    public Group getById(int id);

    public Group getByUsername(String username);

}
