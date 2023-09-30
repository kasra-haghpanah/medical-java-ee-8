package com.kasra.javaee.interfaces.repository;

import com.kasra.javaee.model.Sick;

import javax.ejb.Remote;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by kasra.haghpanah on 12/12/2016.
 */
//@Remote
public interface ISickRepository {

    public Sick save(Sick entity);

    public void delete(Sick entity);

    public void update(Sick entity);

    public List<Sick> getAll();

    public Sick getById(int id);

    public Sick getByName(String firstName , String lastName);

    public List<Sick> getBySickId(int sickId);

    public List<Sick> getByRecourseId(int recourseId);
}
