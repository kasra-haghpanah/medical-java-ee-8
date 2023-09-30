package com.kasra.javaee.jaxb;

import com.kasra.javaee.model.Sick;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by kasra.haghpanah on 19/09/2016.
 */
@XmlRootElement(name = "sicks")
@XmlAccessorType(XmlAccessType.FIELD)
public class Sicks {

    @XmlElement(name = "sick", type = Sick.class)
    List<Sick> sicks = null;

    public List<Sick> getSicks() {
        return sicks;
    }

    public void setSicks(List<Sick> sicks) {
        this.sicks = sicks;
    }
}
