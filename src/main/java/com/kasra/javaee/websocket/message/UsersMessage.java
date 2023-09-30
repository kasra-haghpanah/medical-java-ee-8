package com.kasra.javaee.websocket.message;

import com.google.gson.Gson;

import java.util.Set;

/**
 * Created by kasra.haghpanah on 18/12/2016.
 */
public class UsersMessage implements Message {

    Set<String> users;

    public UsersMessage() {
    }

    public UsersMessage(Set<String> users) {
        this.users = users;
    }

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "{"
                + "\"users\":" + new Gson().toJson(users)
                + "}";
    }
}
