package com.kasra.javaee.controller;

import com.kasra.javaee.framework.web.mvc.annotation.Controller;
import com.kasra.javaee.framework.web.mvc.annotation.WebRequest;
import com.kasra.javaee.framework.web.mvc.servlet.KasraServlet;
import com.kasra.javaee.framework.web.mvc.statics.Method;
import com.kasra.javaee.framework.web.mvc.utility.BrowserUtility;
import com.kasra.javaee.jax.ws.ClientWSForm;
import com.kasra.javaee.model.Sick;
import org.apache.commons.lang.StringEscapeUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.Serializable;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.ProtocolException;
import java.util.List;


/**
 * Created by kasra.haghpanah on 26/08/2016.
 */

@Named
@SessionScoped
@Controller(url = "host")
public class HostController implements Serializable {

    boolean login = false;


    @PostConstruct
    public void init() {
        System.out.println("Start HostController!");
    }

    @WebRequest(url = "getAll", method = Method.GET)
    public void getAll(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, ProtocolException {

        //http://localhost:8084/kasra/host/getAll?id=5&method=post
        String id = request.getParameter("id");
        String method = request.getParameter("method").toUpperCase();
        String path = "http://localhost:8084/kasra/medical/record/sickId?id=" + id;
        String username = "kasra";
        String password = "123";

        BrowserUtility.authentication(false, "localhost", 8084, Method.POST, true, true, username, password);

        String result = (String) BrowserUtility.send(path, Method.POST, null, "application/json", "application/json", false, false, null, null).get(0);
        servlet.print(response, result, "application/json", true);

    }


    @WebRequest(url = "sendJSON", method = Method.GET)
    public void send(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        //http://localhost:8084/kasra/host/sendJSON?id=5&method=post
        String id = request.getParameter("id");
        String method = request.getParameter("method").toUpperCase();
        String username = "kasra";
        String password = "123";

        BrowserUtility.authentication(false, "localhost", 8084, Method.POST, true, true, username, password);

        String url = "http://localhost:8084/kasra/medical/record/getBySickId";
        Sick sick = new Sick();
        sick.setId(Integer.parseInt(id));

        String result = (String) BrowserUtility.send(url, method, sick.toString(), "application/json", "application/json", false, false, null, null).get(0);
        servlet.print(response, result, "application/json", true);

    }


    @WebRequest(url = "upload", method = Method.POST)
    public void upload(
            KasraServlet servlet,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, ServletException {


        BrowserUtility.authentication(false, "localhost", 8084, Method.POST, true, true, "kasra", "123");

        String charset = "UTF-8";
        String requestURL = "";
        String response1 = null;

        for (Part part : request.getParts()) {
            String name = StringEscapeUtils.unescapeJavaScript(part.getName());
            if (name.toLowerCase().equals("port")) {
                requestURL = "http://localhost:" + BrowserUtility.convertBinaryToString(part.getInputStream()) + "/kasra/sick/upload";
            }
        }

        response1 = (String)BrowserUtility.upload(requestURL, Method.POST, "UTF-8", request.getParts()).get(0);
        System.out.println("SERVER REPLIED:");


            servlet.print(response, response1, "text/html", true);


    }


}
