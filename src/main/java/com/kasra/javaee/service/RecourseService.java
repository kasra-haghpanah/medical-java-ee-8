package com.kasra.javaee.service;

import com.kasra.javaee.interceptor.Logged;
import com.kasra.javaee.interfaces.repository.IRecourseRepository;
import com.kasra.javaee.model.Recourse;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

/**
 * Created by kasra.haghpanah on 02/09/2016.
 */
@Named
@RequestScoped
public class RecourseService implements Service<Recourse> {

    @EJB
    IRecourseRepository recourseRepository;

    @Logged
    public Recourse save(Recourse entity) {
        return recourseRepository.save(entity);
    }
    @Logged
    public void delete(Recourse entity) {
        recourseRepository.delete(entity);
    }
    @Logged
    public void update(Recourse entity){
        recourseRepository.update(entity);
    }

    public List<Recourse> getAll() {
        return recourseRepository.getAll();
    }

    public Recourse getById(int id) {
        return recourseRepository.getById(id);
    }

    public List<Recourse> getBySickId(int sickId) {
        return recourseRepository.getBySickId(sickId);
    }

    public List<Recourse> getByRecourseId(int recourseId) {
        return null;
    }
}
