package com.kasra.javaee.interfaces.repository;

import com.kasra.javaee.model.Log;
import com.kasra.javaee.model.Recourse;
import com.kasra.javaee.model.Sick;

import java.util.List;

/**
 * Created by kasra.haghpanah on 12/12/2016.
 */
//@Remote
public interface IRecourseRepository {

    public Recourse save(Recourse entity);

    public void delete(Recourse entity);

    public void update(Recourse entity);

    public List<Recourse> getAll();

    public Recourse getById(int id);

    public List<Recourse> getBySickId(int sickId);

    public List<Recourse> getByRecourseId(int recourseId);
}
