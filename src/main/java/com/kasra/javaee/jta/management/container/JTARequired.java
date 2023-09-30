package com.kasra.javaee.jta.management.container;

import com.kasra.javaee.jta.exception.DaoException;
import com.kasra.javaee.model.MemberBiography;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by kasra.haghpanah on 06/03/2017.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JTARequired {


    @PersistenceContext(unitName = "batch")
    EntityManager em;

    public void save(MemberBiography memberBiography) throws DaoException {

        em.merge(memberBiography);
    }

    public void saveWithException(MemberBiography memberBiography) throws DaoException {

        em.merge(memberBiography);
        throw new DaoException();
    }
}
