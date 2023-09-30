package com.kasra.javaee.controller;

import com.google.gson.Gson;
import com.kasra.javaee.framework.web.mvc.annotation.Controller;
import com.kasra.javaee.framework.web.mvc.annotation.WebRequest;
import com.kasra.javaee.framework.web.mvc.servlet.KasraServlet;
import com.kasra.javaee.framework.web.mvc.statics.Method;
import com.kasra.javaee.model.Recourse;
import com.kasra.javaee.model.Sick;
import com.kasra.javaee.service.RecourseService;
import com.kasra.javaee.service.Service;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by kasra.haghpanah on 26/08/2016.
 */

@Named
@SessionScoped
@Controller(url = "recourse")
public class RecourseController implements Serializable {

    @Inject
    //@RecourseQ
    RecourseService recourseService;


    @PostConstruct
    public void init() {
        System.out.println("Start RecourseController!");
    }


    @WebRequest(url = "getAll", method = Method.GET)
    public void getAll(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        servlet.print(response, recourseService.getAll().toString(), "application/json", true);

    }

    @WebRequest(url = "getBySickId", method = Method.POST)
    public void getBySickId(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        String data = servlet.getInputRequest(request);

        Sick sick = new Gson().fromJson(data, Sick.class);


        servlet.print(response, recourseService.getBySickId(sick.getId()).toString(), "application/json", true);

    }


    @WebRequest(url = "add", method = Method.PUT)
    public void add(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String data = servlet.getInputRequest(request);

        Recourse recourse = new Gson().fromJson(data, Recourse.class);
        recourse.setDate(new Date());

        recourseService.save(recourse);


        //servlet.print(response, new Gson().toJson(sickService.getAll().toArray()), "application/json", true);
        servlet.print(response, recourseService.getBySickId(recourse.getSick().getId()).toString(), "application/json", true);
    }


    @WebRequest(url = "delete", method = Method.POST)
    public void delete(
            HttpServletResponse response,
            HttpServletRequest request,
            KasraServlet servlet) {

        String data = servlet.getInputRequest(request);
        Recourse recourse = new Gson().fromJson(data, Recourse.class);
        recourseService.delete(recourse);

        servlet.print(response, recourseService.getBySickId(recourse.getSick().getId()).toString(), "application/json", true);
    }


    @WebRequest(url = "update", method = Method.PUT)
    public void update(
            HttpServletResponse response,
            HttpServletRequest request,
            KasraServlet servlet) {

        String data = servlet.getInputRequest(request);
        Recourse recourse = new Gson().fromJson(data, Recourse.class);
        recourse.setDate(new Date());
        recourseService.update(recourse);

        servlet.print(response, recourseService.getBySickId(recourse.getSick().getId()).toString(), "application/json", true);
    }


}
