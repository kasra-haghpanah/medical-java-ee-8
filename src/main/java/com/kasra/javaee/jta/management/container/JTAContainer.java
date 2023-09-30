package com.kasra.javaee.jta.management.container;

import com.kasra.javaee.jta.exception.DaoException;
import com.kasra.javaee.model.Member;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by kasra.haghpanah on 07/03/2017.
 */
@Stateless
public class JTAContainer {

    @PersistenceContext(unitName = "batch")
    EntityManager em;

    @EJB
    JTARequired jtaRequired;

    public List<Member> save(Member member) throws DaoException {

        em.merge(member);
        jtaRequired.save(member.getMemberBiography());
        try {
            return em.createNamedQuery(Member.GET_ALL).getResultList();
        } catch (NoResultException e) {
            return null;
        }

    }

    public List<Member> saveWithException(Member member) throws DaoException {

        em.merge(member);
        jtaRequired.saveWithException(member.getMemberBiography());
        try {
            return em.createNamedQuery(Member.GET_ALL).getResultList();
        } catch (NoResultException e) {
            return null;
        }

    }


}
