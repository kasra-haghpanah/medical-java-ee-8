package com.kasra.javaee.service;

import com.kasra.javaee.interceptor.Logged;
import com.kasra.javaee.interfaces.repository.IGroupRepository;
import com.kasra.javaee.interfaces.repository.IUserRepository;
import com.kasra.javaee.model.Group;
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
public class GroupService implements IGroupRepository {

    @EJB
    IGroupRepository groupRepository;


    @Logged
    public Group save(Group entity) {
        return groupRepository.save(entity);
    }

    @Logged
    public void delete(Group entity) {
        groupRepository.delete(entity);
    }

    @Logged
    public void update(Group entity) {
        groupRepository.update(entity);
    }


    public List<Group> getAll() {
        return groupRepository.getAll();
    }


    public Group getById(int id) {
        return groupRepository.getById(id);
    }


    public Group getByUsername(String username) {
        return groupRepository.getByUsername(username);
    }


}
