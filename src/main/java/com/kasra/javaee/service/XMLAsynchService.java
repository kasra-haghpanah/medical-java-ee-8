package com.kasra.javaee.service;


import com.kasra.javaee.interfaces.repository.ISickRepository;
import com.kasra.javaee.jaxb.Sicks;
import com.kasra.javaee.model.Sick;

import javax.ejb.*;
import java.io.Serializable;
import java.util.concurrent.Future;

/**
 * Created by kasra.haghpanah on 02/09/2016.
 */
@Stateless
public class XMLAsynchService implements Serializable {


    @EJB
    ISickRepository sickRepository;

    @Asynchronous
    @AccessTimeout(-1)
    public Future<String> database(Sicks sicks) {


        for (Sick sick : sicks.getSicks()) {
            sickRepository.save(sick);
        }

        return new AsyncResult<String>("success");

    }
}
