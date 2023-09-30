package com.kasra.javaee.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by kasra.haghpanah on 16/09/2016.
 */
@Cacheable(true)
@Entity
@Table(name = "log")
@NamedQueries(
        {
                @NamedQuery(name = "getAllLog", query = "SELECT l From com.kasra.javaee.model.Log l")
        }
)
public class Log implements Serializable {

    public Log() {
    }

    public final static String GET_ALL = "getAllLog";


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "class")
    private String className;

    @Column(name = "method")
    private String method;


    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @Column(name = "message" , length = 30000)
    private String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{"
                + "\"id\":\"" + id + "\""
                + ",\"className\":\"" + className + "\""
                + ",\"method\":\"" + method + "\""
                + ",\"date\":" + date
                + ",\"message\":\"" + message + "\""
                + "}";
    }
}
