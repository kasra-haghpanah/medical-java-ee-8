package com.kasra.javaee.model;

import org.apache.commons.lang.StringEscapeUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by kasra.haghpanah on 16/09/2016.
 */
@Cacheable(true)
@Entity
@Table(name = "medical_record")
@NamedQueries(
        {
                @NamedQuery(name = "getAllMedicalRecord", query = "SELECT s From com.kasra.javaee.model.MedicalRecord s"),
                @NamedQuery(name = "getByIdMedicalRecord", query = "SELECT m From com.kasra.javaee.model.MedicalRecord m WHERE m.id =:id"),
                @NamedQuery(name = "getBySickIdMedicalRecord", query = "SELECT  mr From com.kasra.javaee.model.MedicalRecord mr JOIN mr.sick s WHERE s.id =:sick_id" /*, lockMode = LockModeType.READ*/)

        }
)
public class MedicalRecord implements Serializable {

    public MedicalRecord() {
    }

    public final static String GET_BY_ID = "getByIdMedicalRecord";
    public final static String GET_ALL = "getAllMedicalRecord";
    public final static String GET_BY_SICK_ID = "getBySickIdMedicalRecord";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @NotNull
    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "sick_id",
            referencedColumnName = "id"/*,
            foreignKey = @ForeignKey(name = "fk_medicalrecord_sick")*/
    )
    private Sick sick;

    @Column(name = "category")
    private String category;

    @Column(name = "description", length = 600)
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    private Date date;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {

        //description = StringEscapeUtils.escapeJava(description);
        //category = StringEscapeUtils.escapeJava(category);

        return "{" +
                "\"id\":" + id +
                ",\"sick\":" + sick +
                ",\"category\":\"" + category + '\"' +
                ",\"description\":\"" + description + '\"' +
                ",\"date\":\"" + date + '\"' +
                '}';
    }
}
