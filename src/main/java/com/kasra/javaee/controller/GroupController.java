package com.kasra.javaee.controller;

import com.google.gson.Gson;
import com.kasra.javaee.framework.web.mvc.annotation.Controller;
import com.kasra.javaee.framework.web.mvc.annotation.WebRequest;
import com.kasra.javaee.framework.web.mvc.servlet.KasraServlet;
import com.kasra.javaee.framework.web.mvc.statics.Method;
import com.kasra.javaee.model.Group;
import com.kasra.javaee.model.User;
import com.kasra.javaee.service.GroupService;
import com.kasra.javaee.service.UserService;

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
@Controller(url = "group")
public class GroupController implements Serializable {

    @Inject
    GroupService groupService;

    @PostConstruct
    public void init() {
        System.out.println("Start GroupController!");
    }


    @WebRequest(url = "add", method = Method.PUT)
    public void add(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String data = servlet.getInputRequest(request);

        Group group = new Gson().fromJson(data, Group.class);


        groupService.save(group);


        //servlet.print(response, new Gson().toJson(sickService.getAll().toArray()), "application/json", true);
        servlet.print(response, groupService.getAll().toString(), "application/json", true);
    }


    @WebRequest(url = "getAll", method = Method.POST)
    public void getAll(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        servlet.print(response, groupService.getAll().toString(), "application/json", true);

    }


    @WebRequest(url = "delete", method = Method.POST)
    public void delete(
            HttpServletResponse response,
            HttpServletRequest request,
            KasraServlet servlet) {

        String data = servlet.getInputRequest(request);
        Group group = new Gson().fromJson(data, Group.class);
        groupService.delete(group);

        servlet.print(response, groupService.getAll().toString(), "application/json", true);
    }


    @WebRequest(url = "update", method = Method.PUT)
    public void update(
            HttpServletResponse response,
            HttpServletRequest request,
            KasraServlet servlet) {

        String data = servlet.getInputRequest(request);
        Group group = new Gson().fromJson(data, Group.class);
        groupService.update(group);

        servlet.print(response, groupService.getAll().toString(), "application/json", true);
    }


}
