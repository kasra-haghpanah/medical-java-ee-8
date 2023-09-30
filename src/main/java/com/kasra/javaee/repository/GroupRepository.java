package com.kasra.javaee.repository;

import com.kasra.javaee.interfaces.repository.IGroupRepository;
import com.kasra.javaee.interfaces.repository.IGroupRepositoryRemote;
import com.kasra.javaee.interfaces.repository.IUserRepository;
import com.kasra.javaee.interfaces.repository.IUserRepositoryRemote;
import com.kasra.javaee.model.Group;
import com.kasra.javaee.model.User;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 * Created by kasra.haghpanah on 02/09/2016.
 */
@javax.ejb.Stateless
@Local(IGroupRepository.class)
@Remote(IGroupRepositoryRemote.class)
public class GroupRepository implements IGroupRepository ,IGroupRepositoryRemote,Serializable {

    @PersistenceContext(unitName="medical")
    EntityManager em;

    public Group save(Group entity) {
        em.persist(entity);
        return entity;
    }

    public void delete(Group entity) {
        em.remove(em.merge(entity));
    }

    public void update(Group entity) {
        em.merge(entity);
    }

    public List<Group> getAll() {

        Query query = em.createNamedQuery(Group.GET_ALL);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }

    }

    public Group getById(int id) {
        return (Group) em.createNamedQuery(Group.GET_BY_ID).setParameter("id", id).getSingleResult();
    }

    @Override
    public Group getByUsername(String username) {
        return (Group) em.createNamedQuery(Group.GET_BY_USERNAME).setParameter("username", username).getSingleResult();
    }


}
