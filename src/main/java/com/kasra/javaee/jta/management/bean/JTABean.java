package com.kasra.javaee.jta.management.bean;

import com.kasra.javaee.jta.exception.DaoException;
import com.kasra.javaee.jta.management.container.JTARequired;
import com.kasra.javaee.model.Member;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import java.util.List;

/**
 * Created by kasra.haghpanah on 07/03/2017.
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class JTABean {

    @Resource
    private EJBContext transaction;

    @PersistenceContext(unitName = "batch")
    EntityManager em;

    @EJB
    JTARequired required;


    @PostConstruct
    public void init(){
        try {
            transaction.getUserTransaction().setTransactionTimeout(1000);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public List<Member> save(Member member) {

        try {
            try {
                transaction.getUserTransaction().begin();
            } catch (NotSupportedException e) {
                e.printStackTrace();
            } catch (SystemException e) {
                e.printStackTrace();
            }
            em.merge(member);
            required.save(member.getMemberBiography());
            try {
                transaction.getUserTransaction().commit();
            } catch (RollbackException e) {
                e.printStackTrace();
            } catch (HeuristicMixedException e) {
                e.printStackTrace();
            } catch (HeuristicRollbackException e) {
                e.printStackTrace();
            } catch (SystemException e) {
                e.printStackTrace();
            }
        } catch (DaoException ex) {
            ex.printStackTrace();

            try {
                transaction.getUserTransaction().rollback();
            } catch (SystemException e) {
                e.printStackTrace();
            }
        }


        try {
            return em.createNamedQuery(Member.GET_ALL).getResultList();
        } catch (NoResultException e) {
            return null;
        }

    }


}
