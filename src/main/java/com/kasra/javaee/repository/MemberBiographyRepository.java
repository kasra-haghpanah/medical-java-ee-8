package com.kasra.javaee.repository;

import com.kasra.javaee.interfaces.repository.IMemberBiographyRepository;
import com.kasra.javaee.interfaces.repository.IMemberBiographyRepositoryRemote;
import com.kasra.javaee.model.Member;
import com.kasra.javaee.model.MemberBiography;

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
@Local(IMemberBiographyRepository.class)
@Remote(IMemberBiographyRepositoryRemote.class)
public class MemberBiographyRepository implements IMemberBiographyRepository, IMemberBiographyRepositoryRemote, Serializable {

    @PersistenceContext(unitName = "batch")
    EntityManager em;

    @Override
    public MemberBiography save(MemberBiography entity) {
        //em.persist(entity);
        em.merge(entity);
        return entity;
    }

    @Override
    public void delete(MemberBiography entity) {
        em.remove(em.merge(entity));
    }

    @Override
    public void update(MemberBiography entity) {
        em.merge(entity);
    }

    @Override
    public List<MemberBiography> getAll() {

        Query query = em.createNamedQuery(MemberBiography.GET_ALL);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public MemberBiography getById(int id) {
        return (MemberBiography) em.createNamedQuery(MemberBiography.GET_BY_ID).setParameter("id", id).getSingleResult();
    }

    @Override
    public List<Member> memberJoinByUniversity(String university) {
        return (List<Member>) em.createNamedQuery(MemberBiography.MEMBER_BIOGRAPHY_JOIN_BY_UNIVERSITY, Member.class).setParameter("university", "%" + university + "%").getResultList();
    }

    @Override
    public List<Member> memberJoinGetAll() {
        return (List<Member>) em.createNamedQuery(MemberBiography.MEMBER_BIOGRAPHY_JOIN_GET_ALL, Member.class).getResultList();
    }


}
