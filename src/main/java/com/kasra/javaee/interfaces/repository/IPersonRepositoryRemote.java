package com.kasra.javaee.interfaces.repository;

import com.kasra.javaee.model.Person;

import java.util.List;

/**
 * Created by kasra.haghpanah on 12/12/2016.
 */
//@Remote
public interface IPersonRepositoryRemote {

    public Person save(Person entity);

    public void delete(Person entity);

    public void update(Person entity);

    public List<Person> getAll();

    public Person getById(int id);
    
}
