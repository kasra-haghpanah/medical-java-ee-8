package com.kasra.javaee.service;

import com.kasra.javaee.interceptor.Logged;
import com.kasra.javaee.interfaces.repository.IMemberRepository;
import com.kasra.javaee.interfaces.repository.IPersonRepository;
import com.kasra.javaee.model.Member;
import com.kasra.javaee.model.Person;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 * Created by kasra.haghpanah on 02/09/2016.
 */
@Named
@SessionScoped
public class MemberService implements IMemberRepository , Serializable {

    @EJB
    IMemberRepository iMemberRepository;

    @Logged
    @Override
    public Member save(Member entity) {
        return iMemberRepository.save(entity);
    }

    @Logged
    @Override
    public void delete(Member entity) {
        iMemberRepository.delete(entity);
    }

    @Logged
    @Override
    public void update(Member entity) {
        iMemberRepository.update(entity);
    }

    @Override
    public List<Member> getAll() {
        return iMemberRepository.getAll();
    }

    @Override
    public Member getById(int id) {
        return iMemberRepository.getById(id);
    }

    @Override
    public List<Member> getByMemberJoinLastName(String lastName) {
        return iMemberRepository.getByMemberJoinLastName(lastName);
    }

    @Override
    public List<Member> memberJoinGetAll() {
        return iMemberRepository.memberJoinGetAll();
    }

}
