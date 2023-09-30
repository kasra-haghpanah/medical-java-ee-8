package com.kasra.javaee.cdi.alternative.bean;

import com.kasra.javaee.cdi.alternative.interfaces.Basic;
import com.kasra.javaee.cdi.alternative.qualifier.Two;
import com.kasra.javaee.interceptor.Logged;

import javax.annotation.Priority;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Alternative;
import javax.interceptor.Interceptor;

/**
 * Created by kasra.haghpanah on 03/03/2017.
 */

// https://docs.jboss.org/weld/reference/latest/en-US/html/specialization.html
@Alternative
@Priority(2)
public class AlternativeTwo implements Basic {

    String name = "AlternativeTwo";

    public AlternativeTwo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
