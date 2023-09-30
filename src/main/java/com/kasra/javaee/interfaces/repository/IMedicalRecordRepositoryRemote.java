package com.kasra.javaee.interfaces.repository;

import com.kasra.javaee.model.Log;
import com.kasra.javaee.model.MedicalRecord;
import com.kasra.javaee.model.Sick;

import java.util.List;

/**
 * Created by kasra.haghpanah on 12/12/2016.
 */
//@Remote
public interface IMedicalRecordRepositoryRemote {

    public MedicalRecord save(MedicalRecord entity);

    public void delete(MedicalRecord entity);

    public void update(MedicalRecord entity);

    public List<MedicalRecord> getAll();

    public MedicalRecord getById(int id);

    public List<MedicalRecord> getBySickId(int sickId);

    public List<MedicalRecord> getByRecourseId(int recourseId);
}
