package com.kasra.javaee.repository;

import com.kasra.javaee.interfaces.repository.IRecourseRepository;
import com.kasra.javaee.interfaces.repository.IRecourseRepositoryRemote;
import com.kasra.javaee.model.MedicalRecord;
import com.kasra.javaee.model.Recourse;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

/**
 * Created by kasra.haghpanah on 02/09/2016.
 */
@Stateless
@Local(IRecourseRepository.class)
@Remote(IRecourseRepositoryRemote.class)
public class RecourseRepository implements IRecourseRepository , IRecourseRepositoryRemote , Serializable {

    @PersistenceContext(unitName="medical")
    EntityManager em;

    public Recourse save(Recourse entity) {
        em.persist(entity);
        return entity;
    }

    public void delete(Recourse entity) {
        em.remove(em.merge(entity));
    }

    public void update(Recourse entity) {
        em.merge(entity);
    }

    public List<Recourse> getAll() {
        return em.createNamedQuery(Recourse.GET_ALL).getResultList();
    }

    public Recourse getById(int id) {
        return (Recourse) em.createNamedQuery(Recourse.GET_BY_ID).setParameter("id", id).getSingleResult();
    }

    public List<Recourse> getBySickId(int sickId) {

        return (List<Recourse>) em.createNamedQuery(Recourse.GET_BY_SICK_ID).setParameter("sick_id", sickId).getResultList();
    }

    public List<Recourse> getByRecourseId(int recourseId) {
        return null;
    }
}
