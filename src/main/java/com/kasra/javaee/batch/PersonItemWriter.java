package com.kasra.javaee.batch;

import com.kasra.javaee.model.Person;
import com.kasra.javaee.service.PersonService;

import javax.batch.api.chunk.ItemWriter;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

/**
 * Created by kasra.haghpanah on 13/02/2017.
 */
@Named
public class PersonItemWriter implements ItemWriter{

    @Inject
    PersonService personService;

    public void open(Serializable serializable) throws Exception {
    }

    public void close() throws Exception {
    }

    public void writeItems(List<Object> list) throws Exception {
        for (Object object : list) {
            Person person = (Person) object;
            personService.save(person);
        }
    }

    public Serializable checkpointInfo() throws Exception {
        return new LineCheckpoint();
    }
}
