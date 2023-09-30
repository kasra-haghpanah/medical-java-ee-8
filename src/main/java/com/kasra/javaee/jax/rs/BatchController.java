package com.kasra.javaee.jax.rs;

import com.kasra.javaee.model.Person;
import com.kasra.javaee.service.PersonService;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;

/**
 * Created by kasra.haghpanah on 13/02/2017.
 */

@Named
@SessionScoped
@Path("/batch")
@ApplicationPath("/resources")
public class BatchController implements Serializable {

    @Inject
    PersonService personService;

    @POST
    @Path(("/firstJobChunkSimple"))
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> firstJobChunkSimple() {
        // http://localhost:8084/resources/batch/firstJobChunkSimple
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        long id = jobOperator.start("FirstJobChunkSimple", new Properties());

        return personService.getAll();
    }

    @POST
    @Path(("/mapperPartitionBatch"))
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> mapperPartitionBatch() {
        // http://localhost:8084/resources/batch/mapperPartitionBatch
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        long id = jobOperator.start("FirstJobChunkWithMapperPartition", new Properties());

        return personService.getAll();
    }

    @POST
    @Path(("/planPartitionBatch"))
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> planPartitionBatch() {
        // http://localhost:8084/resources/batch/planPartitionBatch
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        long id = jobOperator.start("FirstJobChunkWithPlanPartition", new Properties());

        return personService.getAll();

    }

    @POST
    @Path(("/secondJobWithBatchlet"))
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> secondJobWithBatchlet() {
        // http://localhost:8084/resources/batch/secondJobWithBatchlet
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        long id = jobOperator.start("SecondJobWithBatchlet", new Properties());

        return personService.getAll();
    }


    @POST
    @Path(("/delete"))
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Person> delete(Person person) {
        // http://localhost:8084/resources/batch/delete
        personService.delete(person);
        return personService.getAll();
    }

    @GET
    @Path(("/getAll"))
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> getAll(){
        // http://localhost:8084/resources/batch/getAll
        return personService.getAll();
    }


}
