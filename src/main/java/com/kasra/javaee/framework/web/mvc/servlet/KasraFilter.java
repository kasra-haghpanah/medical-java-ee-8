package com.kasra.javaee.framework.web.mvc.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by kasra.haghpanah on 15/12/2016.
 */
@WebFilter(filterName = "KasraFilter", urlPatterns = {"/*"})
public class KasraFilter implements Filter {

    private static String serverName = null;


    private String getServerName() {

        InputStream inputStream = this.getClass().getResourceAsStream("/config.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties.getProperty("server-name");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        if (serverName == null) {
            serverName = getServerName();
            if (serverName == null) {
                serverName = "";
            }
        }


        HttpServletResponse httpReponse = ((HttpServletResponse) response);


        HttpServletRequest httpRequest = (HttpServletRequest) request;

        //httpReponse.addHeader("ip", httpRequest.getRemoteUser());

        if (!serverName.equals("")) {
            httpReponse.setHeader("Server", serverName);
            httpReponse.setHeader("X-Powered-By", serverName);
        }

//        httpReponse.setHeader("Host", "localhost:8080");
//        httpReponse.setHeader("Origin", "http://localhost:8080");


        final Map<String, String[]> props = new HashMap<>();


        HttpServletResponseWrapper wrapperResponse = new HttpServletResponseWrapper(httpReponse) {
            public void setHeader(String name, String value) {
                if (!name.equalsIgnoreCase("Upgrade1")) {
                    super.setHeader(name, value);
                }

            }

            public void addHeader(String name, String value) {
                if (!(name.equalsIgnoreCase("Upgrade1"))) {
                    super.addHeader(name, value);
                }
            }
        };


        // Add properties of interest from session; session ID
        // is just for example
        HttpServletRequestWrapper wrappedRequest = new HttpServletRequestWrapper(httpRequest) {


        };


        chain.doFilter(request, wrapperResponse);


        ///////////////////////////////////////////////////////////////////
//        String path = ((HttpServletRequest) request).getRequestURI();
//        if (path.toLowerCase().endsWith(".jsp") || path.toLowerCase().endsWith("j_security_check")) {
//            request.getRequestDispatcher("java").forward(request, response);
//        }
        ///////////////////////////////////////////////////////////////////
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }
}
