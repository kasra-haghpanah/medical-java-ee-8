package com.kasra.javaee.repository;

import com.kasra.javaee.interfaces.repository.IPrescriptionRepository;
import com.kasra.javaee.interfaces.repository.IPrescriptionRepositoryRemote;
import com.kasra.javaee.model.Prescription;
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
@Local(IPrescriptionRepository.class)
@Remote(IPrescriptionRepositoryRemote.class)
public class PrescriptionRepository implements IPrescriptionRepository , IPrescriptionRepositoryRemote , Serializable {

    @PersistenceContext(unitName="medical")
    EntityManager em;

    public Prescription save(Prescription entity) {
        em.persist(entity);
        return entity;
    }

    public void delete(Prescription entity) {
        em.remove(em.merge(entity));
    }

    public void update(Prescription entity) {
        em.merge(entity);
    }

    public List<Prescription> getAll() {
        return em.createNamedQuery(Recourse.GET_ALL).getResultList();
    }

    public Prescription getById(int id) {
        return (Prescription) em.createNamedQuery(Prescription.GET_BY_ID).setParameter("id", id).getSingleResult();
    }

    public List<Prescription> getBySickId(int sickId) {
        return null;
    }

    public List<Prescription> getByRecourseId(int recourseId) {
        return (List<Prescription>) em.createNamedQuery(Prescription.GET_BY_RECOURSE_ID).setParameter("recourse_id", recourseId).getResultList();
    }
}
