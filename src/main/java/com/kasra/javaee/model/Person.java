package com.kasra.javaee.model;

import javax.persistence.*;

/**
 * Created by kasra.haghpanah on 10/03/2017.
 */
@Entity
@Table(name = "person")
@NamedQueries(
        {
                @NamedQuery(name = "getAllPerson", query = "SELECT p From com.kasra.javaee.model.Person p"),
                @NamedQuery(name = "getByPersonId", query = "SELECT p From com.kasra.javaee.model.Person p WHERE p.id =:id")
        }
)
public class Person {


    public final static String GET_BY_ID = "getByPersonId";
    public final static String GET_ALL = "getAllPerson";

    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "FIRST_NAME")
    String firstName;
    @Column(name = "LAST_NAME")
    String lastName;

    public Person() {
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(long id, String firstName, String lastName) {
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "{"
                + "\"id\":\"" + id + "\""
                + ",\"firstName\":\"" + firstName + "\""
                + ",\"lastName\":\"" + lastName + "\""
                + "}";
    }
}
