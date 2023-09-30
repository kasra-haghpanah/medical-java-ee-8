package com.kasra.javaee.websocket.encoder;

import com.kasra.javaee.websocket.message.SketchMessage;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Created by kasra.haghpanah on 19/12/2016.
 */
public class SketchMessageEncoder implements Encoder.Text<SketchMessage>  {
    @Override
    public String encode(SketchMessage sketchMessage) throws EncodeException {
        return sketchMessage.toString();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
