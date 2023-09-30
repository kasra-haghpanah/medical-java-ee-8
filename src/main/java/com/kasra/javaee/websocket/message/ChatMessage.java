package com.kasra.javaee.websocket.message;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by kasra.haghpanah on 14/12/2016.
 */
public class ChatMessage implements Message {

    String name;
    String message;
    String location;
    String room;

    public ChatMessage() {
    }

    public ChatMessage(String name, String message) {
        this.name = name;
        this.message = message;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "{"
                + "\"name\":\"" + name + "\""
                + ",\"message\":\"" + message + "\""
                + ",\"location\":\"" + location + "\""
                + ",\"room\":\"" + room + "\""
                + "}";
    }
}


