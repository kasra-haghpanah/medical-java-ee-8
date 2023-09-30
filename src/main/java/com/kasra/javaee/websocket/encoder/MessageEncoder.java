package com.kasra.javaee.websocket.encoder;

import com.kasra.javaee.websocket.message.ChatMessage;
import com.kasra.javaee.websocket.message.Message;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Created by kasra.haghpanah on 18/12/2016.
 */
public class MessageEncoder implements Encoder.Text<Message> {
    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public String encode(Message message) throws EncodeException {

        if(message instanceof ChatMessage){
            return message.toString();
        }
        return message.toString();
    }
}
