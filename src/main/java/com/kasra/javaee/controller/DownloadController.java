package com.kasra.javaee.controller;

import com.kasra.javaee.model.Sick;
import com.kasra.javaee.framework.web.mvc.annotation.Controller;
import com.kasra.javaee.framework.web.mvc.annotation.WebRequest;
import com.kasra.javaee.framework.web.mvc.servlet.KasraServlet;
import com.kasra.javaee.framework.web.mvc.statics.Method;
import com.kasra.javaee.framework.web.mvc.statics.Status;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * Created by kasra.haghpanah on 06/09/2016.
 */
@Named
@ApplicationScoped
@Controller(url = Status.DISABLED)
public class DownloadController implements Serializable {


    @PostConstruct
    public void init() {
        System.out.println("Start DownloadController!");
    }


    @WebRequest
    public void start(HttpServletRequest request,
                      HttpServletResponse response,
                      KasraServlet servlet) {

        //servlet.getBean(SickRepository.class)
        Sick sick = new Sick();
        sick.setId(12);
        sick.setFirstName("kasra");
        sick.setLastName("haghpanah");
        sick.setSex((short) 1);

        byte[] bytes = servlet.serialize(sick);
        servlet.download(response, "file.ejb", bytes);

    }


    @WebRequest(url = "favicon.ico", method = Method.GET)
    public void icon(HttpServletRequest request,
                     HttpServletResponse response,
                     KasraServlet servlet) {

        String path = servlet.getContextRoot() + "/src/main/webapp/upload/kasra.jpg";
        servlet.download(response, path);

    }


    @WebRequest(url = "login.html", method = Method.GET)
    public void login(HttpServletRequest request,
                     HttpServletResponse response,
                     KasraServlet servlet) {

        String path = servlet.getContextRoot() + "/src/main/webapp/login.html";
        servlet.print(response , servlet.readTextFile(path) , "text/html" , true);

    }

}
