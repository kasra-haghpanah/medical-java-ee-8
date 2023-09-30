package com.kasra.javaee.cdi.bean;

import com.kasra.javaee.cdi.qualifier.MyEvent;

/**
 * Created by kasra.haghpanah on 03/03/2017.
 */
@MyEvent
public class MyEventBean {

    String firstName;
    String lastName;

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

    public MyEventBean() {
    }

    @Override
    public String toString() {
        return "{"
                + "\"firstName\":\"" + firstName + "\""
                + ",\"lastName\":\"" + lastName + "\""
                + "}";
    }


}
