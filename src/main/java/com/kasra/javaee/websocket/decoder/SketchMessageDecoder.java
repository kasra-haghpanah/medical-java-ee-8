package com.kasra.javaee.websocket.decoder;

import com.google.gson.Gson;
import com.kasra.javaee.websocket.message.SketchMessage;

import javax.json.Json;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;

/**
 * Created by kasra.haghpanah on 19/12/2016.
 */
public class SketchMessageDecoder implements Decoder.Text<SketchMessage> {

    @Override
    public SketchMessage decode(String message) throws DecodeException {
        return new Gson().fromJson(message , SketchMessage.class);
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
