package com.kasra.javaee.controller;

import com.google.gson.Gson;
import com.kasra.javaee.framework.web.mvc.annotation.Controller;
import com.kasra.javaee.framework.web.mvc.annotation.WebRequest;
import com.kasra.javaee.framework.web.mvc.servlet.KasraServlet;
import com.kasra.javaee.framework.web.mvc.statics.Method;
import com.kasra.javaee.model.Sick;
import com.kasra.javaee.model.User;
import com.kasra.javaee.service.SickService;
import com.kasra.javaee.service.UserService;

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
@Controller(url = "user")
public class UserController implements Serializable {

    @Inject
    UserService userService;

    @PostConstruct
    public void init() {
        System.out.println("Start UserController!");
    }


    @WebRequest(url = "add", method = Method.PUT)
    public void add(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String data = servlet.getInputRequest(request);

        User[] users = new Gson().fromJson(data, User[].class);

        for (User user : users) {
            userService.save(user);
        }

        //servlet.print(response, new Gson().toJson(sickService.getAll().toArray()), "application/json", true);
        servlet.print(response, userService.getAll().toString(), "application/json", true);
    }


    @WebRequest(url = "getAll", method = Method.POST)
    public void getAll(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        servlet.print(response, userService.getAll().toString(), "application/json", true);

    }


    @WebRequest(url = "delete", method = Method.POST)
    public void delete(
            HttpServletResponse response,
            HttpServletRequest request,
            KasraServlet servlet) {

        String data = servlet.getInputRequest(request);
        User user = new Gson().fromJson(data, User.class);
        userService.delete(user);

        servlet.print(response, userService.getAll().toString(), "application/json", true);
    }


    @WebRequest(url = "update", method = Method.PUT)
    public void update(
            HttpServletResponse response,
            HttpServletRequest request,
            KasraServlet servlet) {

        String data = servlet.getInputRequest(request);
        User user = new Gson().fromJson(data, User.class);
        userService.update(user);

        servlet.print(response, userService.getAll().toString(), "application/json", true);
    }


}
