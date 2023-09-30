package com.kasra.javaee.websocket.decoder;

import com.google.gson.Gson;
import com.kasra.javaee.websocket.message.ChatMessage;
import com.kasra.javaee.websocket.message.Message;

import javax.json.Json;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;


/**
 * Created by kasra.haghpanah on 18/12/2016.
 */
public class MessageDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(String message) throws DecodeException {
        ChatMessage chat = new Gson().fromJson(message , ChatMessage.class);
        return chat;
    }

    @Override
    public boolean willDecode(String message) {
        boolean flag = true;
        try {
            Json.createReader(new StringReader(message)).readObject();
        }
        catch (Exception e){
            flag = false;
        }

        return flag;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
