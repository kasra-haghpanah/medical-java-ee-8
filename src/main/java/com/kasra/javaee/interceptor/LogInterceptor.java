package com.kasra.javaee.interceptor;

import com.kasra.javaee.model.Log;
import com.kasra.javaee.service.LogService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kasra.haghpanah on 16/09/2016.
 */
@Interceptor
@Logged

public class LogInterceptor implements Serializable {
    Map<String, Map<String, Map<Object[], Object>>> cached = new HashMap();


    @Inject
    LogService logService;

    @AroundInvoke
    public Object log(InvocationContext context) {

        Log logged = new Log();

        try {
            Object object = context.proceed();
            //System.out.println(context.getMethod());
            return object;
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            logged.setMessage(errors.toString());
            return null;
        } finally {
            logged.setClassName(context.getMethod().getDeclaringClass().getName());
            logged.setMethod(context.getMethod().getName());
            logged.setDate(new Date());
            logService.save(logged);
        }
    }
}
