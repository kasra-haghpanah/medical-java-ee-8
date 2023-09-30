package com.kasra.javaee.interfaces.repository;

import com.kasra.javaee.model.Prescription;
import com.kasra.javaee.model.Sick;

import java.util.List;

/**
 * Created by kasra.haghpanah on 12/12/2016.
 */
//@Remote
public interface IPrescriptionRepositoryRemote {

    public Prescription save(Prescription entity);

    public void delete(Prescription entity);

    public void update(Prescription entity);

    public List<Prescription> getAll();

    public Prescription getById(int id);

    public List<Prescription> getBySickId(int sickId);

    public List<Prescription> getByRecourseId(int recourseId);
}
