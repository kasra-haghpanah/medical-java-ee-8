package com.kasra.javaee.cdi.bean;

import com.kasra.javaee.cdi.qualifier.First;
import com.kasra.javaee.cdi.qualifier.Second;
import com.kasra.javaee.interceptor.Logged;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Named;
import java.io.Serializable;

/**
 * Created by kasra.haghpanah on 03/03/2017.
 */

@Named
@RequestScoped
public class Product implements Serializable {

    private String first = "";
    private String second = "";

    @PostConstruct
    public void start() {
        first += "{";
        second += "{";
    }

    @PreDestroy
    public void end() {
        first += "}";
        second += "}";
    }

    @Produces
    @First
    public String getFirstString(InjectionPoint injectionPoint) {
//        if(injectionPoint.getQualifiers().contains(First.class)){
        String value = injectionPoint.getAnnotated().getAnnotation(First.class).value();
        String name = injectionPoint.getAnnotated().getAnnotation(First.class).name();
        first += (" value: " + value + ", name:" + name);
        return "your anotated value is:" + first;
    }


    @Produces
    @Second
    public String getSecondString(InjectionPoint injectionPoint) {
//        if(injectionPoint.getQualifiers().contains(Second.class)){
        second += (" value: " + injectionPoint.getAnnotated().getAnnotation(Second.class).value());
        return "your anotated value is:" + second;
    }


    public void firstClose(@Disposes @First String getFirstString) {
        first += " jvm finalize";
        System.out.println("end of by dispose  that mean's before jvm finalize");
    }


    public void secondClose(@Disposes @Second String getSecondString) {
        second += " jvm finalize";
        System.out.println("end of by dispose  that mean's before jvm finalize");
    }

}
