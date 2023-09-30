package com.kasra.javaee.repository;

import com.kasra.javaee.interfaces.repository.IMedicalRecordRepository;
import com.kasra.javaee.interfaces.repository.IMedicalRecordRepositoryRemote;
import com.kasra.javaee.model.MedicalRecord;
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
@Local(IMedicalRecordRepository.class)
@Remote(IMedicalRecordRepositoryRemote.class)
public class MedicalRecordRepository implements IMedicalRecordRepository , IMedicalRecordRepositoryRemote , Serializable {

    @PersistenceContext(unitName="medical")
    EntityManager em;

    public MedicalRecord save(MedicalRecord entity) {
        em.persist(entity);
        return entity;
    }

    public void delete(MedicalRecord entity) {
        em.remove(em.merge(entity));
    }

    public void update(MedicalRecord entity) {
        em.merge(entity);
    }

    public List<MedicalRecord> getAll() {
        return em.createNamedQuery(MedicalRecord.GET_ALL).getResultList();
    }

    public MedicalRecord getById(int id) {
        return (MedicalRecord) em.createNamedQuery(MedicalRecord.GET_BY_ID).setParameter("id", id).getSingleResult();
    }

    public List<MedicalRecord> getBySickId(int sickId) {

        return (List<MedicalRecord>) em.createNamedQuery(MedicalRecord.GET_BY_SICK_ID).setParameter("sick_id", sickId).getResultList();
    }

    public List<MedicalRecord> getByRecourseId(int recourseId) {
        return null;
    }
}
