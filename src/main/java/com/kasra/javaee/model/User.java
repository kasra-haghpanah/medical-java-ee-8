package com.kasra.javaee.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;


/**
 * Created by kasra.haghpanah on 11/01/2017.
 */
@Cacheable(true)
@Entity
@Table(name = "user")
@NamedQueries(
        {
                @NamedQuery(name = "getAllUser", query = "SELECT u From com.kasra.javaee.model.User u"),
                @NamedQuery(name = "getByIdUser", query = "SELECT u From com.kasra.javaee.model.User u WHERE u.id =:id"),
                @NamedQuery(name = "getByUserNameUser", query = "SELECT u From com.kasra.javaee.model.User u WHERE u.username =:username")
        }
)
public class User implements Serializable {


    public final static String GET_BY_ID = "getByIdUser";
    public final static String GET_ALL = "getAllUser";
    public final static String GET_BY_NAME = "getByUserNameUser";

    User() {
    }

    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    //@Basic(optional = false)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "{"
                + "\"id\":\"" + id + "\""
                + ",\"username\":\"" + username + "\""
                + ",\"password\":\"" + password + "\""
                + "}";
    }
}
