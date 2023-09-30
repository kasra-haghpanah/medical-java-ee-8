package com.kasra.javaee.controller;

import com.google.gson.Gson;
import com.kasra.javaee.framework.web.mvc.annotation.Controller;
import com.kasra.javaee.framework.web.mvc.annotation.WebRequest;
import com.kasra.javaee.framework.web.mvc.servlet.KasraServlet;
import com.kasra.javaee.framework.web.mvc.statics.Method;
import com.kasra.javaee.model.MedicalRecord;
import com.kasra.javaee.model.Sick;
import com.kasra.javaee.service.MedicalRecordService;

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
@Controller(url = "medical/record")
public class MedicalRecordController implements Serializable {

    @Inject
    //@MedicalRecordQ
            MedicalRecordService medicalRecordService;


    @PostConstruct
    public void init() {
        System.out.println("Start MedicalRecordController!");
    }


    @WebRequest(url = "getAll", method = Method.GET)
    public void getAll(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        servlet.print(response, medicalRecordService.getAll().toString(), "application/json", true);

    }

    @WebRequest(url = "getBySickId", method = Method.POST)
    public void getBySickId(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        String data = servlet.getInputRequest(request);

        Sick sick = new Gson().fromJson(data, Sick.class);


        servlet.print(response, medicalRecordService.getBySickId(sick.getId()).toString(), "application/json", true);

    }


    @WebRequest(url = "add", method = Method.PUT)
    public void add(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String data = servlet.getInputRequest(request);

        MedicalRecord medicalRecord = new Gson().fromJson(data, MedicalRecord.class);
        medicalRecord.setDate(new Date());

        medicalRecordService.save(medicalRecord);


        //servlet.print(response, new Gson().toJson(sickService.getAll().toArray()), "application/json", true);
        servlet.print(response, medicalRecordService.getBySickId(medicalRecord.getSick().getId()).toString(), "application/json", true);
    }


    @WebRequest(url = "delete", method = Method.POST)
    public void delete(
            HttpServletResponse response,
            HttpServletRequest request,
            KasraServlet servlet) {

        String data = servlet.getInputRequest(request);
        MedicalRecord medicalRecord = new Gson().fromJson(data, MedicalRecord.class);
        medicalRecordService.delete(medicalRecord);

        servlet.print(response, medicalRecordService.getBySickId(medicalRecord.getSick().getId()).toString(), "application/json", true);
    }


    @WebRequest(url = "update", method = Method.PUT)
    public void update(
            HttpServletResponse response,
            HttpServletRequest request,
            KasraServlet servlet) {

        String data = servlet.getInputRequest(request);
        MedicalRecord medicalRecord = new Gson().fromJson(data, MedicalRecord.class);
        medicalRecord.setDate(new Date());
        medicalRecordService.update(medicalRecord);

        servlet.print(response, medicalRecordService.getBySickId(medicalRecord.getSick().getId()).toString(), "application/json", true);
    }


    @WebRequest(url = "sickId", method = Method.POST)
    public void getBySickIdParam(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        int id = Integer.parseInt(request.getParameter("id"));

        //int id = 3;
        servlet.print(response, medicalRecordService.getBySickId(id).toString(), "application/json", true);

    }


}
