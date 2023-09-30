package com.kasra.javaee.service;

import com.kasra.javaee.interceptor.Logged;
import com.kasra.javaee.interfaces.repository.IPersonRepository;
import com.kasra.javaee.interfaces.repository.IUserRepository;
import com.kasra.javaee.model.Person;
import com.kasra.javaee.model.User;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by kasra.haghpanah on 02/09/2016.
 */
@Named
public class PersonService implements IPersonRepository {

    @EJB
    IPersonRepository iPersonRepository;


    @Logged
    public Person save(Person entity) {
        return iPersonRepository.save(entity);
    }

    @Logged
    public void delete(Person entity) {
        iPersonRepository.delete(entity);
    }

    @Logged
    public void update(Person entity) {
        iPersonRepository.update(entity);
    }

    public List<Person> getAll() {
        return iPersonRepository.getAll();
    }

    public Person getById(int id) {
        return iPersonRepository.getById(id);
    }
}
