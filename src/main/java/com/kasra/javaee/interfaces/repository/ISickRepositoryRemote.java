package com.kasra.javaee.interfaces.repository;

import com.kasra.javaee.model.Sick;

import java.util.List;

/**
 * Created by kasra.haghpanah on 12/12/2016.
 */
//@Remote
public interface ISickRepositoryRemote {

    public Sick save(Sick entity);

    public void delete(Sick entity);

    public void update(Sick entity);

    public List<Sick> getAll();

    public Sick getById(int id);

    public Sick getByName(String firstName , String lastName);

    public List<Sick> getBySickId(int sickId);

    public List<Sick> getByRecourseId(int recourseId);
}
