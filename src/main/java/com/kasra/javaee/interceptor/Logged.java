package com.kasra.javaee.interceptor;

import javax.enterprise.context.NormalScope;
import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

/**
 * Created by kasra.haghpanah on 16/09/2016.
 */
@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface Logged {
}
