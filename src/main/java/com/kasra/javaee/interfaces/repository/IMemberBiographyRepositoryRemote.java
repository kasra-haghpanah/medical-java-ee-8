package com.kasra.javaee.interfaces.repository;

import com.kasra.javaee.model.Member;
import com.kasra.javaee.model.MemberBiography;

import java.util.List;

/**
 * Created by kasra.haghpanah on 12/12/2016.
 */
//@Remote
public interface IMemberBiographyRepositoryRemote {

    public MemberBiography save(MemberBiography entity);

    public void delete(MemberBiography entity);

    public void update(MemberBiography entity);

    public List<MemberBiography> getAll();

    public MemberBiography getById(int id);

    public List<Member> memberJoinByUniversity(String university);

    public List<Member> memberJoinGetAll();

}
