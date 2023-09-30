package com.kasra.javaee.framework.web.mvc.annotation;

/**
 * Created by kasra.haghpanah on 26/08/2016.
 */

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Controller {
    public String url() default "" ;
}
