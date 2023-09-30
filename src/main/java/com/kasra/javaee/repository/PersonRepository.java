package com.kasra.javaee.repository;

import com.kasra.javaee.interfaces.repository.IPersonRepository;
import com.kasra.javaee.interfaces.repository.IPersonRepositoryRemote;
import com.kasra.javaee.model.Person;
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
@Local(IPersonRepository.class)
@Remote(IPersonRepositoryRemote.class)
public class PersonRepository implements IPersonRepository,IPersonRepositoryRemote,Serializable {

    @PersistenceContext(unitName="batch")
    EntityManager em;

    public Person save(Person entity) {
        em.persist(entity);
        return entity;
    }

    public void delete(Person entity) {
        em.remove(em.merge(entity));
    }

    public void update(Person entity) {
        em.merge(entity);
    }

    public List<Person> getAll() {

        Query query = em.createNamedQuery(Person.GET_ALL);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }

    }

    public Person getById(int id) {
        return (Person) em.createNamedQuery(Person.GET_BY_ID).setParameter("id", id).getSingleResult();
    }


}
