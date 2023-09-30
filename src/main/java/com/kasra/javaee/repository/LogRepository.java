package com.kasra.javaee.repository;

import com.kasra.javaee.interfaces.repository.ILogRepository;
import com.kasra.javaee.interfaces.repository.ILogRepositoryRemote;
import com.kasra.javaee.model.Log;

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
@Local(ILogRepository.class)
@Remote(ILogRepositoryRemote.class)
public class LogRepository implements ILogRepository , ILogRepositoryRemote , Serializable {

    @PersistenceContext(unitName="medical")
    EntityManager em;

    public Log save(Log entity) {
        em.persist(entity);
        return entity;
    }

    public void delete(Log entity) {
        em.remove(em.merge(entity));
    }

    public void update(Log entity){
        em.merge(entity);
    }

    public List<Log> getAll() {

        return em.createNamedQuery(Log.GET_ALL).getResultList();
    }

    public Log getById(int id) {
        return null;
    }

    public List<Log> getBySickId(int sickId) {
        return null;
    }

    public List<Log> getByRecourseId(int recourseId) {
        return null;
    }
}
