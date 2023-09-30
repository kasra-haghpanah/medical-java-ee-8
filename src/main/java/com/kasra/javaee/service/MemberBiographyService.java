package com.kasra.javaee.service;

import com.kasra.javaee.interceptor.Logged;
import com.kasra.javaee.interfaces.repository.IMemberBiographyRepository;
import com.kasra.javaee.interfaces.repository.IMemberRepository;
import com.kasra.javaee.model.Member;
import com.kasra.javaee.model.MemberBiography;

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
public class MemberBiographyService implements IMemberBiographyRepository , Serializable {

    @EJB
    IMemberBiographyRepository iMemberBiographyRepository;


    @Override
    public MemberBiography save(MemberBiography entity) {
        return iMemberBiographyRepository.save(entity);
    }

    @Override
    public void delete(MemberBiography entity) {
        iMemberBiographyRepository.delete(entity);
    }

    @Override
    public void update(MemberBiography entity) {
        iMemberBiographyRepository.update(entity);
    }

    @Override
    public List<MemberBiography> getAll() {
        return iMemberBiographyRepository.getAll();
    }

    @Override
    public MemberBiography getById(int id) {
        return iMemberBiographyRepository.getById(id);
    }

    @Override
    public List<Member> memberJoinByUniversity(String university) {
        return iMemberBiographyRepository.memberJoinByUniversity(university);
    }

    @Override
    public List<Member> memberJoinGetAll() {
        return iMemberBiographyRepository.memberJoinGetAll();
    }
}
