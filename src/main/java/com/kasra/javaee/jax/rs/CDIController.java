package com.kasra.javaee.jax.rs;

import com.kasra.javaee.cdi.alternative.bean.AlternativeOne;
import com.kasra.javaee.cdi.alternative.bean.AlternativeTwo;
import com.kasra.javaee.cdi.alternative.decorator.MyDecorator;
import com.kasra.javaee.cdi.alternative.interfaces.Basic;
import com.kasra.javaee.cdi.alternative.qualifier.One;
import com.kasra.javaee.cdi.bean.MyEventBean;
import com.kasra.javaee.cdi.qualifier.First;
import com.kasra.javaee.cdi.qualifier.MyEvent;
import com.kasra.javaee.cdi.qualifier.QDecorator;
import com.kasra.javaee.cdi.qualifier.Second;
import com.kasra.javaee.interceptor.Logged;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;

/**
 * Created by kasra.haghpanah on 03/03/2017.
 */

@Named
@RequestScoped
@Path("/cdi")
@ApplicationPath("/resources")
public class CDIController implements Serializable {

    @Inject
    @First(value = "1:first", name = "kasra")
    String first;


    @Inject
    @Second(value = "2:second")
    String second;


    @Inject
    @MyEvent
    Event<MyEventBean> event;

    @Inject
    AlternativeTwo basic;

    @Inject
    Basic myDecorator;

    @PUT
    @Path(("/disposes"))
    public Response getDisposes() {
        //localhost:8084/resources/cdi/disposes
        return Response.ok(first + "---->" + second).build();
    }


    @PUT
    @Path(("/event"))
    @Produces(MediaType.APPLICATION_JSON)
    public MyEventBean runEvent() {
        //localhost:8084/resources/cdi/event

        MyEventBean bean = new MyEventBean();
        bean.setFirstName("kasra");
        bean.setLastName("haghpanah");
        event.fire(bean);
        return bean;
    }

    @PUT
    @Path(("/alternative"))
    public Response getAlternative() {
        //localhost:8084/resources/cdi/alternative
        return Response.ok(basic.getName()).build();
    }

    @PUT
    @Path(("/decorator"))
    //Decorators are always called after interceptors.
    public Response getDecorator() {
        //localhost:8084/resources/cdi/decorator
        return Response.ok(basic.getName()).build();
    }

}
