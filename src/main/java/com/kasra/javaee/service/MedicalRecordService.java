package com.kasra.javaee.service;

import com.kasra.javaee.interceptor.Logged;
import com.kasra.javaee.interfaces.repository.IMedicalRecordRepository;
import com.kasra.javaee.model.MedicalRecord;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by kasra.haghpanah on 02/09/2016.
 */
@Named
@RequestScoped
public class MedicalRecordService implements Service<MedicalRecord> {

    //@EJB
    @Inject
    IMedicalRecordRepository medicalRecordRepository;

    @Logged
    public MedicalRecord save(MedicalRecord entity) {
        return medicalRecordRepository.save(entity);
    }

    @Logged
    public void delete(MedicalRecord entity) {
        medicalRecordRepository.delete(entity);
    }

    @Logged
    public void update(MedicalRecord entity) {
        medicalRecordRepository.update(entity);
    }

    public List<MedicalRecord> getAll() {
        return medicalRecordRepository.getAll();
    }

    public MedicalRecord getById(int id) {
        return medicalRecordRepository.getById(id);
    }

    public List<MedicalRecord> getBySickId(int sickId) {
        return medicalRecordRepository.getBySickId(sickId);
    }

    public List<MedicalRecord> getByRecourseId(int recourseId) {
        return null;
    }
}
