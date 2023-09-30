package com.kasra.javaee.websocket.endpoint.server;


import com.kasra.javaee.websocket.configuration.ChatroomConfigurator;
import com.kasra.javaee.websocket.decoder.MessageDecoder;
import com.kasra.javaee.websocket.encoder.MessageEncoder;
import com.kasra.javaee.websocket.message.ChatMessage;
import com.kasra.javaee.websocket.message.Message;
import com.kasra.javaee.websocket.message.UsersMessage;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by kasra.haghpanah on 14/12/2016.
 */
@ServerEndpoint(
        value = "/chatrooms/{room-name}"
        , configurator = ChatroomConfigurator.class
        , encoders = {MessageEncoder.class}
        , decoders = {MessageDecoder.class}
)
public class ChatroomServerEndpoint {


    private static Set<Session> chatroomUsers = Collections.synchronizedSet(new HashSet<Session>());


    @OnOpen
    public void open(Session session
            , EndpointConfig config
            , @PathParam("room-name") String roomName
    ) throws IOException, EncodeException {

        HttpSession httpSession = (HttpSession) config.getUserProperties().get("HttpSession");

        Object usernameAttr = httpSession.getAttribute("username");


        if (usernameAttr != null) {

            boolean existUser = false;

            for (Session session1 : chatroomUsers) {
                if (session1.getUserProperties().get("username").equals(usernameAttr.toString())) {
                    existUser = true;
                    break;
                }
            }

            if (!existUser) {
                String username = usernameAttr.toString();
                String location = httpSession.getAttribute("location").toString();
                session.getUserProperties().put("username", username);
                session.getUserProperties().put("location", location);
                session.getUserProperties().put("room", roomName);
                chatroomUsers.add(session);


                for (Session session1 : chatroomUsers) {
                    UsersMessage usersMessage = new UsersMessage(getUsernames(session1.getUserProperties().get("room").toString()));
                    if (usersMessage.getUsers().size() > 0) {
                        session1.getBasicRemote().sendObject(usersMessage);
                    }
                }


                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setName(username);
                chatMessage.setLocation(location);
                chatMessage.setRoom(session.getUserProperties().get("room").toString());
                chatMessage.setMessage("Welcom to my chatroom!");

                session.getBasicRemote().sendObject(chatMessage);

            } else {
                session.close();
            }
        } else {
            session.close();
//            ChatMessage chatMessage = new ChatMessage();
//            chatMessage.setName(null);
//            chatMessage.setLocation(null);
//            chatMessage.setMessage(null);
//
//            session.getBasicRemote().sendObject(chatMessage);
        }

    }


    @OnMessage
    public void onMessage(Session session, Message incommingMessage) throws IOException, EncodeException {


        if (incommingMessage instanceof ChatMessage) {
            ChatMessage incommingChatMessage = (ChatMessage) incommingMessage;


            ChatMessage outgoingChatMessage = new ChatMessage();
            String userName = (String) session.getUserProperties().get("username");
            String location = (String) session.getUserProperties().get("location");

            outgoingChatMessage.setName(userName);
            outgoingChatMessage.setMessage(incommingChatMessage.getMessage());
            outgoingChatMessage.setLocation(location);
            outgoingChatMessage.setRoom(session.getUserProperties().get("room").toString());

            String roomName = session.getUserProperties().get("room").toString();

            for (Session session1 : chatroomUsers) {
                if (session1.getUserProperties().get("room").toString().equals(roomName)) {
                    session1.getBasicRemote().sendObject(outgoingChatMessage);
                }
            }

            for (Session session1 : chatroomUsers) {
                UsersMessage usersMessage = new UsersMessage(getUsernames(session1.getUserProperties().get("room").toString()));
                if (usersMessage.getUsers().size() > 0) {
                    session1.getBasicRemote().sendObject(usersMessage);
                }
            }

        }


    }


    @OnError
    public void error(Throwable t) {
        t.printStackTrace();
    }


    @OnClose
    public void close(Session session, EndpointConfig config) throws IOException, EncodeException {

        HttpSession httpSession = (HttpSession) config.getUserProperties().get("HttpSession");
        httpSession.removeAttribute("username");
        httpSession.removeAttribute("location");

        chatroomUsers.remove(session);

        for (Session session1 : chatroomUsers) {
            UsersMessage usersMessage = new UsersMessage(getUsernames(session1.getUserProperties().get("room").toString()));
            if (usersMessage.getUsers().size() > 0) {
                session1.getBasicRemote().sendObject(usersMessage);
            }
        }

    }

    private Set<String> getUsernames(String roomName) {
        Set<String> users = new HashSet<String>();
        for (Session session : chatroomUsers) {
            if (session.isOpen()) {
                if (session.getUserProperties().get("username") != null && session.getUserProperties().get("room").toString().equals(roomName)) {
                    users.add(session.getUserProperties().get("username").toString());
                }
            }
        }
        return users;
    }


}
