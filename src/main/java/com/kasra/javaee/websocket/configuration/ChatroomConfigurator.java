package com.kasra.javaee.websocket.configuration;



import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;


/**
 * Created by kasra.haghpanah on 14/12/2016.
 */
public class ChatroomConfigurator extends ServerEndpointConfig.Configurator {

    @Override
    public void modifyHandshake(ServerEndpointConfig config,
                                HandshakeRequest request,
                                HandshakeResponse response
    ) {

        HttpSession httpSession = (HttpSession) request.getHttpSession();

        config.getUserProperties().put("HttpSession", httpSession);

        //config.getUserProperties().put(HandshakeResponse.class.getName(),response);

        //super.modifyHandshake(config, request, response);

    }
}

