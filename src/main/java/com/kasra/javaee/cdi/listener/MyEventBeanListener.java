package com.kasra.javaee.cdi.listener;

import com.kasra.javaee.cdi.bean.MyEventBean;
import com.kasra.javaee.cdi.qualifier.MyEvent;

import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;

/**
 * Created by kasra.haghpanah on 03/03/2017.
 */
public class MyEventBeanListener {

    public void observe(@Observes(notifyObserver = Reception.ALWAYS) @MyEvent MyEventBean event){
        System.out.println("Account: " + event.toString());
    }
}
