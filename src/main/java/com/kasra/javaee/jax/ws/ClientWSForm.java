package com.kasra.javaee.jax.ws;

import com.kasra.javaee.framework.web.mvc.statics.Method;
import com.kasra.javaee.framework.web.mvc.utility.BrowserUtility;
import com.kasra.javaee.jax.ws.proxy.*;
import com.kasra.javaee.jax.ws.proxy.ServerWS;
import scala.util.parsing.combinator.testing.Str;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by kasra.haghpanah on 08/command/2017.
 */

public class ClientWSForm {

    private static List<String> cookies = new ArrayList<String>();

    public static void main(String[] args) {

        String username = "kasra";
        String password = "123";

        System.out.println(BrowserUtility.authentication(false, "localhost", 8084, Method.POST, true, false, username, password));

        com.kasra.javaee.jax.ws.proxy.ServerWS serverWS = (ServerWS) new Biography().getServerWSPort();
        String name = serverWS.name("kasra");
        System.out.println(name);

    }


}
