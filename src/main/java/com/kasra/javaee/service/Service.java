package com.kasra.javaee.service;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kasra.haghpanah on 02/09/2016.
 */
public interface Service<T> extends Serializable{

    public T save(T entity);
    public void delete(T entity);
    public void update(T entity);
    public List<T> getAll();
    public T getById(int id);
    public List<T> getBySickId(int sickId);
    public List<T> getByRecourseId(int recourseId);
}
