package com.kasra.javaee.cdi.alternative.decorator;

import com.kasra.javaee.cdi.alternative.interfaces.Basic;
import com.kasra.javaee.cdi.alternative.qualifier.One;
import com.kasra.javaee.cdi.alternative.qualifier.Three;
import com.kasra.javaee.cdi.qualifier.QDecorator;

import javax.annotation.Priority;
import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.interceptor.Interceptor;

/**
 * Created by kasra.haghpanah on 03/03/2017.
 */

@Dependent
@Decorator
public class MyDecorator implements Basic {


    @Inject
    @Any
    @Delegate
    Basic basic;

    @Override
    public String getName() {
        return "Decorator:{" + basic.getName() + "}";
    }

    @Override
    public void setName(String name) {
        basic.setName(name);
    }
}
