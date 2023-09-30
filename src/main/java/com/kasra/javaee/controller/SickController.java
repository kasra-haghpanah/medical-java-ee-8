package com.kasra.javaee.controller;

import com.google.gson.Gson;
import com.kasra.javaee.framework.web.mvc.annotation.Controller;
import com.kasra.javaee.framework.web.mvc.annotation.WebRequest;
import com.kasra.javaee.framework.web.mvc.servlet.KasraServlet;
import com.kasra.javaee.framework.web.mvc.statics.Method;
import com.kasra.javaee.model.Sick;
import com.kasra.javaee.service.SickService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by kasra.haghpanah on 26/08/2016.
 */

@Named
@SessionScoped
@Controller(url = "sick")
public class SickController implements Serializable {

    @Inject
    SickService sickService;

    @PostConstruct
    public void init() {
        System.out.println("Start SickController!");
    }


    @WebRequest(url = "add", method = Method.PUT)
    public void add(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String data = servlet.getInputRequest(request);

        Sick[] array = new Gson().fromJson(data, Sick[].class);
        List<Sick> sicks = Arrays.asList(array);

        for (Sick sick : sicks) {
            sickService.save(sick);
        }

        //servlet.print(response, new Gson().toJson(sickService.getAll().toArray()), "application/json", true);
        servlet.print(response, sickService.getAll().toString(), "application/json", true);
    }


    @WebRequest(url = "getAll", method = Method.POST)
    public void getAll(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        servlet.print(response, sickService.getAll().toString(), "application/json", true);

    }


    @WebRequest(url = "delete", method = Method.POST)
    public void delete(
            HttpServletResponse response,
            HttpServletRequest request,
            KasraServlet servlet) {

        String data = servlet.getInputRequest(request);
        Sick sick = new Gson().fromJson(data, Sick.class);
        sickService.delete(sick);

        servlet.print(response, sickService.getAll().toString(), "application/json", true);
    }


    @WebRequest(url = "update", method = Method.PUT)
    public void update(
            HttpServletResponse response,
            HttpServletRequest request,
            KasraServlet servlet) {

        String data = servlet.getInputRequest(request);
        Sick sick = new Gson().fromJson(data, Sick.class);
        sickService.update(sick);

        servlet.print(response, sickService.getAll().toString(), "application/json", true);
    }


    @WebRequest(url = "upload", method = Method.POST)
    public void mySample(
            HttpServletResponse response,
            HttpServletRequest request,
            KasraServlet servlet) {

        //String path = servlet.getServletContext().getRealPath("").replace("\\", "/");
        //String folderTarget = path.substring(0, path.indexOf("/target")) + "/src/main/webapp/upload/";
        String folderTarget = servlet.getContextRoot() + "/src/main/webapp/upload/";

        Map<String, String[]> map1 = servlet.upload(request, folderTarget, true);
        for (String key : map1.keySet()) {
            for (String value : map1.get(key)) {
                System.out.println(key + ":" + value);
            }

        }

        System.out.println("----------------------------------------------------------" + request.getContentType());


        servlet.print(response, "upload successfully!", "text/html", true);



/*
        Map<String, String[]> map = request.getParameterMap();

        for (String parameter : map.keySet()) {
            Object object = map.get(parameter);

            if (object.getClass().getName().indexOf("[Ljava") > -1) {
                String args = "";
                for (String req : request.getParameterValues(parameter)) {
                    args += req + ",";
                }

                args = args.substring(0, args.length() - 1);
                System.out.println(parameter + " : " + args);
            } else {
                System.out.println(parameter + " : " + request.getParameter(parameter));
            }

        }
        */


    }

}
