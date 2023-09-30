package com.kasra.javaee.jax.ws;


import com.kasra.javaee.cdi.alternative.interfaces.Basic;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jws.WebMethod;
import javax.jws.WebService;


/**
 * Created by kasra.haghpanah on 08/01/2017.
 */
@WebService(
        serviceName = "biography",
        targetNamespace = "http://ws.jax.javaee.kasra.com/"//,
        //wsdlLocation = "http://localhost:8084//biography?wsdl"
)
//@Named
public class ServerWS {

//    @Inject
//    Basic basic;


    @WebMethod(operationName = "name")
    public String getName(String name) {

        return "Your name is : " /*+ basic.getName()*/ + "------" + name;
    }


}
