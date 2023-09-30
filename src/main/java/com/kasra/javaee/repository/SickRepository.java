package com.kasra.javaee.repository;

import com.kasra.javaee.interfaces.repository.ISickRepository;
import com.kasra.javaee.interfaces.repository.ISickRepositoryRemote;
import com.kasra.javaee.model.Sick;

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
@Stateless
@Local(ISickRepository.class)
@Remote(ISickRepositoryRemote.class)
public class SickRepository implements ISickRepository ,ISickRepositoryRemote ,Serializable {

    @PersistenceContext(unitName="medical")
    EntityManager em;

    public Sick save(Sick entity) {
        em.persist(entity);
        return entity;
    }

    public void delete(Sick entity) {
        em.remove(em.merge(entity));
    }

    public void update(Sick entity) {
        em.merge(entity);
    }

    public List<Sick> getAll() {

        Query query = em.createNamedQuery(Sick.GET_ALL);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }

    }

    public Sick getById(int id) {
        return (Sick) em.createNamedQuery(Sick.GET_BY_ID).setParameter("id", id).getSingleResult();
    }

    @Override
    public Sick getByName(String firstName, String lastName) {
        return (Sick) em.createNamedQuery(Sick.GET_BY_NAME).setParameter("firstName", firstName).setParameter("lastName", lastName).getSingleResult();
    }

    public List<Sick> getBySickId(int sickId) {
        return null;
    }

    public List<Sick> getByRecourseId(int recourseId) {
        return null;
    }
}
