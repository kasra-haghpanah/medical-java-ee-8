package com.kasra.javaee.jax.ws;

import com.kasra.javaee.jax.ws.proxy.Biography;

import com.kasra.javaee.jax.ws.proxy.ServerWS;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;


/**
 * Created by kasra.haghpanah on 08/command/2017.
 */

public class ClientWSBasic {

    public static void main(String[] args) {


        Authenticator myAuth = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("kasra", "123".toCharArray());
            }
        };

        Authenticator.setDefault(myAuth);


        //Desktop.getDesktop().browse(new URI("http://localhost:8084/#/host/upload"));

        ServerWS serverWS = (ServerWS) new Biography().getServerWSPort();
        String name = serverWS.name("kasra");
        System.out.println(name);
    }

}
