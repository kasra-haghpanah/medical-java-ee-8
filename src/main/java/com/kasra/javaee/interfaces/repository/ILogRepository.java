package com.kasra.javaee.interfaces.repository;

import com.kasra.javaee.model.Log;
import com.kasra.javaee.model.Sick;

import java.util.List;

/**
 * Created by kasra.haghpanah on 12/12/2016.
 */
//@Remote
public interface ILogRepository {

    public Log save(Log entity);

    public void delete(Log entity);

    public void update(Log entity);

    public List<Log> getAll();

    public Log getById(int id);

    public List<Log> getBySickId(int sickId);

    public List<Log> getByRecourseId(int recourseId);
}
