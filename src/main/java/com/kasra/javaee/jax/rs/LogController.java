package com.kasra.javaee.jax.rs;

import com.kasra.javaee.jta.exception.DaoException;
import com.kasra.javaee.jta.management.bean.JTABean;
import com.kasra.javaee.jta.management.container.JTAContainer;
import com.kasra.javaee.model.Log;
import com.kasra.javaee.model.Member;
import com.kasra.javaee.service.LogService;
import com.kasra.javaee.service.MemberBiographyService;
import com.kasra.javaee.service.MemberService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.List;

/**
 * Created by kasra.haghpanah on 06/03/2017.
 */
@Named
@SessionScoped
@Path("/log")
@ApplicationPath("/resources")
public class LogController implements Serializable {


    @Inject
    LogService logService;


    @POST
    @Path(("/getAll"))
    @Produces(MediaType.APPLICATION_JSON)
    public List<Log> getAll() throws DaoException {

        //localhost:8084/resources/log/getAll
        return logService.getAll();

    }


    @PUT
    @Path(("/delete"))
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Log> delete(Log log) throws DaoException {

        //localhost:8084/resources/log/delete
        logService.delete(log);
        return logService.getAll();

    }


}
