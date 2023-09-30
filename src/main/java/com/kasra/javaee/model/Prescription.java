package com.kasra.javaee.model;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by kasra.haghpanah on 16/09/2016.
 */
@Cacheable(true)
@Entity
@Table(name = "prescription")
@NamedQueries(
        {
                @NamedQuery(name = "getAllPrescription", query = "SELECT p From com.kasra.javaee.model.Prescription p"),
                @NamedQuery(name = "getByIdPrescription", query = "SELECT p From com.kasra.javaee.model.Prescription p WHERE p.id =:id"),
                @NamedQuery(name = "getByRecourseId", query = "SELECT p From com.kasra.javaee.model.Prescription p JOIN p.recourse r WHERE r.id =:recourse_id")
        }
)
public class Prescription implements Serializable {


    public Prescription() {
    }

    public final static String GET_ALL = "getAllPrescription";
    public final static String GET_BY_ID = "getByIdPrescription";
    public final static String GET_BY_RECOURSE_ID = "getByRecourseId";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "recourse_id",
            referencedColumnName = "id"/*,
            foreignKey = @ForeignKey(name = "fk_prescription_recourse")*/
    )
    private Recourse recourse;

    @Column(name = "description", length = 600)
    private String description;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Recourse getRecourse() {
        return recourse;
    }

    public void setRecourse(Recourse recourse) {
        this.recourse = recourse;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {

        //description = StringEscapeUtils.escapeJava(description);

        return "{" +
                "\"id\":" + id +
                ",\"recourse\":" + recourse +
                ",\"description\":\"" + description + '\"' +
                '}';
    }
}
