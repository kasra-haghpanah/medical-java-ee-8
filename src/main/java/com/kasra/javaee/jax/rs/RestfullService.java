package com.kasra.javaee.jax.rs;

import com.kasra.javaee.framework.web.mvc.utility.BrowserUtility;
import com.kasra.javaee.model.Sick;
import com.kasra.javaee.service.SickService;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.PathParam;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.List;

/**
 * Created by kasra.haghpanah on 13/02/2017.
 */
@Named
@SessionScoped
@Path("/MyRestService")
@ApplicationPath("/resources")
//@RolesAllowed({"user","admin"})
public class RestfullService implements Serializable {

    @Inject
    SickService sickService;


    @GET
    @Path(("/hello"))
    public void getHelloMessage(
            @Context SecurityContext context,
            @Context HttpServletResponse servletResponse,
            @Context HttpServletRequest servletRequest
    ) {
        //localhost:8084/resources/MyRestService/hello
        String username = context.getUserPrincipal().getName();

        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setContentLength(username.length());
        PrintWriter out = null;
        try {
            out = servletResponse.getWriter();
            out.print(username);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //return "Hello Word!";
    }


    @GET
    @Path(("/echo"))
    public Response getEchoMessage(@QueryParam("message") String message) {
        //localhost:8084/resources/MyRestService/echo?message=kasra
        return Response.ok("Your message was:" + message).build();

    }


    @GET
    @Path(("/user/firstname/{fname}/lastname/{lname}"))
    @RolesAllowed({"admin", "user"})
    public Response getEchoUser(@PathParam(value = "fname") String fname, @PathParam(value = "lname") String lname) {
        //localhost:8084/resources/MyRestService/user/firstname/kasra/lastname/haghpanah
        return Response.ok("Your name is:" + fname + " " + lname).build();

    }

    @POST
    @Path("/xml/getSicks")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    public List<Sick> getAllSicksByXML(List<Sick> sickList) {
        //localhost:8084/resources/MyRestService/xml/getSicks
        return sickList;
    }

    @POST
    @Path("/json/getSicks")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Sick getAllSicksByJSON(List<Sick> sicks) {
        //localhost:8084/resources/MyRestService/json/getSicks
        return sicks.get(0);
    }

    @GET
    @Path(("/asynch/user/{fname}/delay/{delay}"))
    public Response getEchoUser(@Suspended AsyncResponse ar, @PathParam(value = "fname") String fname, @PathParam(value = "delay") int delay) {
        //localhost:8084/resources/MyRestService/asynch/user/kasra/delay/1
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
        }

        Response response = Response.ok("Your name is:" + fname).build();
        ar.resume(response);
        BrowserUtility.writeTextFile("E:/test/" + fname + ".txt", fname);
        return response;

    }
}
