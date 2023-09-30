package com.kasra.javaee.jax.rs;

import com.kasra.javaee.interceptor.Logged;
import com.kasra.javaee.jta.exception.DaoException;
import com.kasra.javaee.jta.management.bean.JTABean;
import com.kasra.javaee.jta.management.container.JTAContainer;
import com.kasra.javaee.model.Member;
import com.kasra.javaee.model.MemberBiography;
import com.kasra.javaee.model.Person;
import com.kasra.javaee.service.MemberBiographyService;
import com.kasra.javaee.service.MemberService;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.List;

/**
 * Created by kasra.haghpanah on 06/03/2017.
 */
@Named
@SessionScoped
@Path("/jta")
@ApplicationPath("/resources")
public class JTAController implements Serializable {


    @Inject
    MemberService memberService;

    @Inject
    MemberBiographyService memberBiographyService;

    @EJB
    JTABean jtaBean;

    @EJB
    JTAContainer jtaContainer;


    @PUT
    @Path(("/save/container/member"))
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Member> saveContainerMember(Member member) throws DaoException {

        //localhost:8084/resources/jta/save/container/member
        return jtaContainer.save(member);

    }

    @Logged
    @PUT
    @Path(("/save/container/exception/member"))
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Member> saveContainerMemberWithException(Member member) throws DaoException {

        //localhost:8084/resources/jta/save/container/exception/member
        return jtaContainer.saveWithException(member);

    }

    @Logged
    @PUT
    @Path(("/save/bean/member"))
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Member> saveBeanMember(Member member){

        //localhost:8084/resources/jta/save/bean/member
        return jtaBean.save(member);

    }




    @Logged
    @PUT
    @Path(("/save/member"))
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Member> saveMember(Member member){

        //localhost:8084/resources/jta/save/member
        memberService.save(member);
        return memberService.getAll();
    }


    @PUT
    @Path(("/update/member"))
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Member> updateMember(Member member){

        //localhost:8084/resources/jta/update/member
        memberService.save(member);
        memberBiographyService.save(member.getMemberBiography());
        return memberService.getAll();
    }


    @PUT
    @Path(("/delete/member"))
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Member> delete(Member member){

        //localhost:8084/resources/jta/delete/member
        memberService.delete(member);
        return memberService.getAll();
    }

    @GET
    @Path(("/member/all"))
    @Produces(MediaType.APPLICATION_JSON)
    public List<Member> all(){

        //localhost:8084/resources/jta/member/all
        return memberService.getAll();
    }


    @GET
    @Path(("/native/member/lastname/{lname}"))
    @Produces(MediaType.APPLICATION_JSON)
    public List<Member> getMemberById(@PathParam(value = "lname") String lastname){

        //localhost:8084/resources/jta/native/member/lastname/haghpanah
        return memberService.getByMemberJoinLastName(lastname);
    }


    @GET
    @Path(("/native/member/getAll"))
    @Produces(MediaType.APPLICATION_JSON)
    public List<Member> getNativeMemberAll(){

        //localhost:8084/resources/jta/native/member/getAll
        return memberService.memberJoinGetAll();
    }





}
