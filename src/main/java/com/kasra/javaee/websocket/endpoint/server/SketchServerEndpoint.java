package com.kasra.javaee.websocket.endpoint.server;

import com.kasra.javaee.websocket.decoder.SketchMessageDecoder;
import com.kasra.javaee.websocket.encoder.SketchMessageEncoder;
import com.kasra.javaee.websocket.message.SketchMessage;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kasra.haghpanah on 19/12/2016.
 */
@ServerEndpoint(
        value = "/sketchServerEndpoint",
        decoders = {SketchMessageDecoder.class},
        encoders = {SketchMessageEncoder.class}
)
public class SketchServerEndpoint {

    private static Set<Session> sketchUsers = Collections.synchronizedSet(new HashSet<Session>());


    @OnOpen
    public void open(Session session) {

        sketchUsers.add(session);
        System.out.println("Client is now connected...");
    }


    @OnMessage
    public void onMessage(Session sessions, SketchMessage incommingSketchMessage) throws IOException, EncodeException {

        SketchMessage outgoingSketchMessage = new SketchMessage();
        outgoingSketchMessage.setX(incommingSketchMessage.getX());
        outgoingSketchMessage.setY(incommingSketchMessage.getY());
        outgoingSketchMessage.setSize(incommingSketchMessage.getSize());
        outgoingSketchMessage.setColor(incommingSketchMessage.getColor());

        for (Session session : sketchUsers) {
            session.getBasicRemote().sendObject(outgoingSketchMessage);
        }


    }


    @OnError
    public void error(Throwable t) {
        t.printStackTrace();
    }


    @OnClose
    public void close(Session session) {

        sketchUsers.remove(session);
    }


}
