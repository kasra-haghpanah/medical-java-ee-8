package com.kasra.javaee.cdi.alternative.bean;

import com.kasra.javaee.cdi.alternative.interfaces.Basic;
import com.kasra.javaee.cdi.alternative.qualifier.One;
import com.kasra.javaee.interceptor.Logged;

import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Default;
import javax.interceptor.Interceptor;

/**
 * Created by kasra.haghpanah on 03/03/2017.
 */

// https://docs.jboss.org/weld/reference/latest/en-US/html/specialization.html
@Default
@Alternative
public class AlternativeOne implements Basic {

    String name = "AlternativeOne";

    public AlternativeOne(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
