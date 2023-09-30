package com.kasra.javaee.repository;

import com.kasra.javaee.interfaces.repository.IMemberRepository;
import com.kasra.javaee.interfaces.repository.IMemberRepositoryRemote;
import com.kasra.javaee.model.Member;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

/**
 * Created by kasra.haghpanah on 02/09/2016.
 */
@Stateless
@Local(IMemberRepository.class)
@Remote(IMemberRepositoryRemote.class)
public class MemberRepository implements IMemberRepository, IMemberRepositoryRemote, Serializable {

    @PersistenceContext(unitName = "batch")
    EntityManager em;

    @Override
    public Member save(Member entity) {
        //em.persist(entity);
        em.merge(entity);
        return entity;
    }

    @Override
    public void delete(Member entity) {
        em.remove(em.merge(entity));
    }

    @Override
    public void update(Member entity) {
        em.merge(entity);
    }

    @Override
    public List<Member> getAll() {

        Query query = em.createNamedQuery(Member.GET_ALL);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public Member getById(int id) {
        return (Member) em.createNamedQuery(Member.GET_BY_ID).setParameter("id", id).getSingleResult();
    }

    @Override
    public List<Member> getByMemberJoinLastName(String lastName) {
        return (List<Member>) em.createNamedQuery(Member.MEMBER_JOIN_LASTNAME, Member.class).setParameter("lastname", "%" + lastName + "%").getResultList();
    }

    @Override
    public List<Member> memberJoinGetAll() {
        return (List<Member>) em.createNamedQuery(Member.MEMBER_JOIN_GET_ALL, Member.class).getResultList();
    }


}
