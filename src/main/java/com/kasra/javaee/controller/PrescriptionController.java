package com.kasra.javaee.controller;

import com.google.gson.Gson;
import com.kasra.javaee.framework.web.mvc.annotation.Controller;
import com.kasra.javaee.framework.web.mvc.annotation.WebRequest;
import com.kasra.javaee.framework.web.mvc.servlet.KasraServlet;
import com.kasra.javaee.framework.web.mvc.statics.Method;
import com.kasra.javaee.model.Prescription;
import com.kasra.javaee.model.Recourse;
import com.kasra.javaee.service.PrescriptionService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * Created by kasra.haghpanah on 26/08/2016.
 */

@Named
@SessionScoped
@Controller(url = "prescription")
public class PrescriptionController implements Serializable {

    @Inject
    //@PrescriptionQ
    PrescriptionService prescriptionService;


    @PostConstruct
    public void init() {
        System.out.println("Start PrescriptionController!");
    }


    @WebRequest(url = "getAll", method = Method.GET)
    public void getAll(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        servlet.print(response, prescriptionService.getAll().toString(), "application/json", true);

    }

    @WebRequest(url = "getByRecourseId", method = Method.POST)
    public void getBySickId(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        String data = servlet.getInputRequest(request);

        Recourse recourse = new Gson().fromJson(data, Recourse.class);


        servlet.print(response, prescriptionService.getByRecourseId(recourse.getId()).toString(), "application/json", true);

    }


    @WebRequest(url = "add", method = Method.PUT)
    public void add(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String data = servlet.getInputRequest(request);

        Prescription prescription = new Gson().fromJson(data, Prescription.class);


        prescriptionService.save(prescription);


        //servlet.print(response, new Gson().toJson(sickService.getAll().toArray()), "application/json", true);
        servlet.print(response, prescriptionService.getByRecourseId(prescription.getRecourse().getId()).toString(), "application/json", true);
    }


    @WebRequest(url = "delete", method = Method.POST)
    public void delete(
            HttpServletResponse response,
            HttpServletRequest request,
            KasraServlet servlet) {

        String data = servlet.getInputRequest(request);
        Prescription prescription = new Gson().fromJson(data, Prescription.class);
        prescriptionService.delete(prescription);

        servlet.print(response, prescriptionService.getByRecourseId(prescription.getRecourse().getId()).toString(), "application/json", true);
    }


    @WebRequest(url = "update", method = Method.PUT)
    public void update(
            HttpServletResponse response,
            HttpServletRequest request,
            KasraServlet servlet) {

        String data = servlet.getInputRequest(request);
        Prescription prescription = new Gson().fromJson(data, Prescription.class);
        prescriptionService.update(prescription);

        servlet.print(response, prescriptionService.getByRecourseId(prescription.getRecourse().getId()).toString(), "application/json", true);
    }


}
