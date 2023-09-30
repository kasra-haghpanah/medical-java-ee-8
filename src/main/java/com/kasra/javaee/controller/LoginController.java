package com.kasra.javaee.controller;

import com.google.gson.Gson;
import com.kasra.javaee.framework.web.mvc.annotation.Controller;
import com.kasra.javaee.framework.web.mvc.annotation.WebRequest;
import com.kasra.javaee.framework.web.mvc.servlet.KasraServlet;
import com.kasra.javaee.framework.web.mvc.statics.Method;
import com.kasra.javaee.websocket.message.ChatMessage;


import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * Created by kasra.haghpanah on 20/12/2016.
 */
@Named
@SessionScoped
@Controller(url = "login")
public class LoginController implements Serializable{


    @PostConstruct
    public void init() {
        System.out.println("Start LoginController!");
    }

    @WebRequest(url = "signin", method = Method.PUT)
    public void signin(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        String data = servlet.getInputRequest(request);
        ChatMessage user = new Gson().fromJson(data, ChatMessage.class);

        if(request.getSession().getAttribute("username") == null) {
            request.getSession().setAttribute("username", user.getName());
            request.getSession().setAttribute("location", user.getLocation());
        }

        servlet.print(response, "{\"message\":\"your login successfully!\"}", "application/json", true);

    }


    @WebRequest(url = "signout", method = Method.PUT)
    public void signout(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        request.getSession().removeAttribute("username");
        request.getSession().removeAttribute("location");
    }

}
