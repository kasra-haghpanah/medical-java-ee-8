package com.kasra.javaee.service;

import com.kasra.javaee.interceptor.Logged;
import com.kasra.javaee.interfaces.repository.IPrescriptionRepository;
import com.kasra.javaee.model.Prescription;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

/**
 * Created by kasra.haghpanah on 02/09/2016.
 */
@Named
@RequestScoped
public class PrescriptionService implements Service<Prescription> {

    @EJB
    IPrescriptionRepository prescriptionRepository;

    @Logged
    public Prescription save(Prescription entity) {
        return prescriptionRepository.save(entity);
    }

    @Logged
    public void delete(Prescription entity) {
        prescriptionRepository.delete(entity);
    }

    @Logged
    public void update(Prescription entity) {
        prescriptionRepository.update(entity);
    }

    public List<Prescription> getAll() {
        return prescriptionRepository.getAll();
    }

    public Prescription getById(int id) {
        return prescriptionRepository.getById(id);
    }

    public List<Prescription> getBySickId(int sickId) {
        return prescriptionRepository.getBySickId(sickId);
    }

    public List<Prescription> getByRecourseId(int recourseId) {
        return prescriptionRepository.getByRecourseId(recourseId);
    }
}
