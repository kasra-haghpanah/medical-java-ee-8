package com.kasra.javaee.model;

import javax.persistence.*;

/**
 * Created by kasra.haghpanah on 10/03/2017.
 */
@Entity
@Table(name = "member_biography")
@NamedQueries(
        {
                @NamedQuery(name = "getAllMemberBiography", query = "SELECT mb From com.kasra.javaee.model.MemberBiography mb"),
                @NamedQuery(name = "getByMemberBiographyId", query = "SELECT mb From com.kasra.javaee.model.MemberBiography mb WHERE mb.id =:id")
        }
)
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "memberBiographyJoinByUniversity",
                query = "SELECT * FROM member m , member_biography mb WHERE m.member_biography_id = mb.id AND mb.university LIKE ?university",
                resultClass = Member.class),
        @NamedNativeQuery(
                name = "memberBiographyJoinGetAll",
                query = "SELECT * FROM member m , member_biography mb WHERE m.member_biography_id = mb.id",
                resultClass = Member.class)
})
public class MemberBiography {


    public final static String GET_BY_ID = "getAllMemberBiography";
    public final static String GET_ALL = "getByMemberBiographyId";
    public final static String MEMBER_BIOGRAPHY_JOIN_BY_UNIVERSITY = "memberBiographyJoinByUniversity";
    public final static String MEMBER_BIOGRAPHY_JOIN_GET_ALL = "memberBiographyJoinGetAll";

    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "university")
    String university;
    @Column(name = "age")
    int age;


    public MemberBiography() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "{"
                + "\"id\":" + id + ""
                + ",\"university\":\"" + university + "\""
                + ",\"age\":" + age + ""
                + "}";
    }
}
