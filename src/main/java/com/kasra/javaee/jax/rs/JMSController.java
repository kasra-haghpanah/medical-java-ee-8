package com.kasra.javaee.jax.rs;

import com.kasra.javaee.jms.queue.asynch.normal.ProducerAsynchQueue;
import com.kasra.javaee.jms.queue.synch.ConsumerSynchQueue;
import com.kasra.javaee.jms.queue.synch.ProducerSynchQueue;
import com.kasra.javaee.jms.topic.share.undurable.ProducerTopic;
import com.kasra.javaee.model.Person;
import com.kasra.javaee.model.Sick;
import com.kasra.javaee.service.SickService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.List;

/**
 * Created by kasra.haghpanah on 01/12/2016.
 */
@Named
@SessionScoped
@Path("/jms")
@ApplicationPath("/resources")
public class JMSController implements Serializable {

    @Inject
    ProducerAsynchQueue producerAsynchQueue;

    @Inject
    SickService sickService;

    @Inject
    ConsumerSynchQueue consumerSynchQueue;

    @Inject
    ProducerSynchQueue producerSynchQueue;

    @Inject
    ProducerTopic producerTopic;


    @PostConstruct
    public void init() {
        System.out.println("Start JmsController!");
    }



    @PUT
    @Path(("/queue/add"))
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response asynchQueue(Sick sick) {
        //localhost:8084/resources/jms/queue/add
        producerAsynchQueue.send(sick);
        return Response.ok("{\"data\":\"null\"}").build();
    }


    @GET
    @Path(("/queue/synch"))
    @Produces(MediaType.APPLICATION_JSON)
    //@Consumes(MediaType.APPLICATION_JSON)
    public List<Sick> synchQueue(){
        //localhost:8084/resources/jms/queue/synch
        Sick sick = new Sick();
        sick.setId(1);
        sick.setFirstName("kasra1");
        sick.setLastName("haghpanah1");
        sick.setSex(new Short("1"));
        producerSynchQueue.send(sick);
        sick = consumerSynchQueue.recieve();
        return sickService.getAll();
    }



    @PUT
    @Path(("/topic/durable/share"))
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Person> durableShare(List<Person> personList){

        //localhost:8084/resources/jms/topic/durable/share
        producerTopic.send(personList);
        return personList;
    }

}
