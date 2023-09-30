package com.kasra.javaee.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by kasra.haghpanah on 11/01/2017.
 */
@Cacheable(true)
@Entity
@Table(name = "groups")
@NamedQueries(
        {
                @NamedQuery(name = "getAllGroup", query = "SELECT g From com.kasra.javaee.model.Group g"),
                @NamedQuery(name = "getByIdGroup", query = "SELECT g From com.kasra.javaee.model.Group g WHERE g.id =:id"),
                @NamedQuery(name = "getByUsernameGroup", query = "SELECT g From com.kasra.javaee.model.Group g JOIN g.user u WHERE u.username =:username")
        }
)
public class Group implements Serializable{


    public final static String GET_BY_ID = "getByIdGroup";
    public final static String GET_ALL = "getAllGroup";
    public final static String GET_BY_USERNAME = "getByUsernameGroup";

    Group(){}

    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @NotNull
    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_username",
            referencedColumnName = "username"/*,
            foreignKey = @ForeignKey(name = "fk_group_user")*/
    )
    private User user;

    //@Pattern(regexp="(admin|user){1}\\z/i")
    @Column(name = "role")
    private String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return "{"
                + "\"id\":\"" + id + "\""
                + ",\"user\":" + user
                + ",\"role\":\"" + role + "\""
                + "}";
    }
}
