package com.kasra.javaee.controller;

import com.kasra.javaee.framework.web.mvc.annotation.Controller;
import com.kasra.javaee.framework.web.mvc.annotation.WebRequest;
import com.kasra.javaee.framework.web.mvc.servlet.KasraServlet;
import com.kasra.javaee.framework.web.mvc.statics.Method;
import com.kasra.javaee.jaxb.Sicks;
import com.kasra.javaee.service.SickService;
import com.kasra.javaee.service.XMLAsynchService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


/**
 * Created by kasra.haghpanah on 19/09/2016.
 */
@Named
@SessionScoped
@Controller(url = "xml")
public class XMLAsynchController implements Serializable {

    @Inject
    SickService sickService;

    @EJB
    XMLAsynchService xmlAsynchService;

    @PostConstruct
    public void init() {
        System.out.println("Start SickController!");
    }


    @WebRequest(url = "getAll", method = Method.GET)
    public void getAll(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        Sicks sicks = new Sicks();
        sicks.setSicks(sickService.getAll());
        String XML = servlet.writeXml(sicks);
        //servlet.print(response, new Gson().toJson(sickService.getAll().toArray()), "application/json", true);
        servlet.print(response, XML, "application/xml", true);
    }


    @WebRequest(url = "read", method = Method.POST)
    public void read(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String XML = servlet.getInputRequest(request);

        Sicks sicks = (Sicks) servlet.readXML(XML, Sicks.class);
        Future<String> result =  xmlAsynchService.database(sicks);

        if (result.isDone()) {
            try {
                System.out.println(result.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println("done:" + result.isDone());
        } else {
            System.out.println("Still Running...");
        }


    }


}
