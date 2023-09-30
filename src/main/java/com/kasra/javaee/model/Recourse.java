package com.kasra.javaee.model;

import org.apache.commons.lang.StringEscapeUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by kasra.haghpanah on 16/09/2016.
 */
@Cacheable(true)
@Entity
@Table(name = "recourse")
@NamedQueries(
        {
                @NamedQuery(name = "getAllRecourse", query = "SELECT r From com.kasra.javaee.model.Recourse r"),
                @NamedQuery(name = "getByIdRecourse", query = "SELECT r From com.kasra.javaee.model.Recourse r WHERE r.id =:id"),
                @NamedQuery(name = "getBySickIdRecourse", query = "SELECT  r From com.kasra.javaee.model.Recourse r JOIN r.sick s WHERE s.id =:sick_id")
        }
)
public class Recourse implements Serializable {

    public Recourse() {
    }

    public final static String GET_ALL = "getAllRecourse";
    public final static String GET_BY_ID = "getByIdRecourse";
    public final static String GET_BY_SICK_ID = "getBySickIdRecourse";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "sick_id",
            referencedColumnName = "id"/*,
            foreignKey = @ForeignKey(name = "fk_recourse_sick")*/
    )
    private Sick sick;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

    @Column(name = "disease_name")
    private String diseaseName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sick getSick() {
        return sick;
    }

    public void setSick(Sick sick) {
        this.sick = sick;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    @Override
    public String toString() {

        //diseaseName = StringEscapeUtils.escapeJava(diseaseName);

        return "{" +
                "\"id\":" + id +
                ",\"sick\":" + sick +
                ",\"date\":\"" + date + "\"" +
                ",\"diseaseName\":\"" + diseaseName + '\"' +
                '}';
    }
}
