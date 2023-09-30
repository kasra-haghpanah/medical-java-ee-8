package com.kasra.javaee.model;

import org.apache.commons.lang.StringEscapeUtils;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * Created by kasra.haghpanah on 16/09/2016.
 */
@Cacheable(true)
@Entity
@Table(name = "sick")
@NamedQueries(
        {
                @NamedQuery(name = "getAll", query = "SELECT s From com.kasra.javaee.model.Sick s"),
                @NamedQuery(name = "getById", query = "SELECT s From com.kasra.javaee.model.Sick s WHERE s.id =:id"),
                @NamedQuery(name = "getByName", query = "SELECT s From com.kasra.javaee.model.Sick s WHERE s.firstName =:firstName AND s.lastName =:lastName")
        }
)
@XmlRootElement(name = "sick")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlType(propOrder = {"firstName", "lastName", "sex"})
public class Sick implements Serializable {

    public Sick() {
    }

    public final static String GET_BY_ID = "getById";
    public final static String GET_ALL = "getAll";
    public final static String GET_BY_NAME = "getByName";

    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "fname")
    private String firstName;

    @Pattern(regexp="(\\w){3,40}")
    @Column(name = "lname")
    private String lastName;

    @Column(name = "sex")
    private Short sex;

    @XmlAttribute(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lasttName) {
        this.lastName = lasttName;
    }

    public short getSex() {
        return sex;
    }

    public void setSex(short sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {

        return "{" +
                "\"id\":" + id +
                ",\"firstName\":\"" + firstName + '\"' +
                ",\"lastName\":\"" + lastName + '\"' +
                ",\"sex\":" + sex +
                '}';
    }
}
