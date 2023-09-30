package com.kasra.javaee.interfaces.repository;


import com.kasra.javaee.model.Member;


import java.util.List;

/**
 * Created by kasra.haghpanah on 12/12/2016.
 */
//@Remote
public interface IMemberRepositoryRemote {

    public Member save(Member entity);

    public void delete(Member entity);

    public void update(Member entity);

    public List<Member> getAll();

    public Member getById(int id);

    public List<Member> getByMemberJoinLastName(String lastName);

    public List<Member> memberJoinGetAll();

}
