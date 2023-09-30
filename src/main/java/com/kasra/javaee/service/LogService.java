package com.kasra.javaee.service;


import com.kasra.javaee.interfaces.repository.ILogRepository;
import com.kasra.javaee.model.Log;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.util.List;

/**
 * Created by kasra.haghpanah on 02/09/2016.
 */
@Named
@SessionScoped
public class LogService implements Service<Log> {

    @EJB
    ILogRepository logRepository;


    public Log save(Log entity) {
        return logRepository.save(entity);
    }

    public void delete(Log entity) {
        logRepository.delete(entity);
    }

    public void update(Log entity){
        logRepository.update(entity);
    }

    public List<Log> getAll() {
        return logRepository.getAll();
    }

    public Log getById(int id) {
        return logRepository.getById(id);
    }

    public List<Log> getBySickId(int sickId) {
        return null;
    }

    public List<Log> getByRecourseId(int recourseId) {
        return null;
    }
}
