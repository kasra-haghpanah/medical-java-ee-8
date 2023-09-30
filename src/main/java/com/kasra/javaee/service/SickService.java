package com.kasra.javaee.service;

import com.kasra.javaee.interceptor.Logged;
import com.kasra.javaee.interfaces.repository.ISickRepository;
import com.kasra.javaee.model.Sick;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.util.List;

/**
 * Created by kasra.haghpanah on 02/09/2016.
 */
@Named
@SessionScoped
public class SickService implements Service<Sick> {

    @EJB
    ISickRepository sickRepository;

    @Logged
    public Sick save(Sick entity) {
        return sickRepository.save(entity);
    }
    @Logged
    public void delete(Sick entity) {
        sickRepository.delete(entity);
    }
    @Logged
    public void update(Sick entity){
        sickRepository.update(entity);
    }

    public List<Sick> getAll() {
        return sickRepository.getAll();
    }

    public Sick getById(int id) {
        return sickRepository.getById(id);
    }

    public Sick getByName(String firstName, String lastName) {
        return sickRepository.getByName(firstName , lastName);
    }

    public List<Sick> getBySickId(int sickId) {
        return null;
    }

    public List<Sick> getByRecourseId(int recourseId) {
        return null;
    }
}
