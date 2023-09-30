package com.kasra.javaee.model;

import javax.persistence.*;

/**
 * Created by kasra.haghpanah on 10/03/2017.
 */
@Entity
@Table(name = "member")
@NamedQueries(
        {
                @NamedQuery(name = "getAllMember", query = "SELECT m From com.kasra.javaee.model.Member m"),
                @NamedQuery(name = "getByMemberId", query = "SELECT m From com.kasra.javaee.model.Member m WHERE m.id =:id")
        }
)
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "memberJoinByLastName",
                query = "SELECT * FROM member m , member_biography mb WHERE m.member_biography_id = mb.id AND m.lastname LIKE ?lastname ",
                resultClass = Member.class),
        @NamedNativeQuery(
                name = "memberJoinGetAll",
                query = "SELECT *  FROM member m , member_biography mb WHERE m.member_biography_id = mb.id",
                resultClass = Member.class)
})
public class Member {


    public final static String GET_BY_ID = "getByMemberId";
    public final static String GET_ALL = "getAllMember";
    public final static String MEMBER_JOIN_LASTNAME = "memberJoinByLastName";
    public final static String MEMBER_JOIN_GET_ALL = "memberJoinGetAll";


    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "firstname")
    String firstName;
    @Column(name = "lastname")
    String lastName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "member_biography_id",
            referencedColumnName = "id"/*,
            foreignKey = @ForeignKey(name = "fk_member_biography")*/)
    private MemberBiography memberBiography;

    public Member() {
    }

    public Member(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Member(long id, String firstName, String lastName) {
        this.id = id;
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

    public MemberBiography getMemberBiography() {
        return memberBiography;
    }

    public void setMemberBiography(MemberBiography memberBiography) {
        this.memberBiography = memberBiography;
    }


    @Override
    public String toString() {
        return "{"
                + "\"id\":" + id + ""
                + ",\"firstName\":\"" + firstName + "\""
                + ",\"lastName\":\"" + lastName + "\""
                + ",\"memberBiography\":" + memberBiography
                + "}";
    }
}
