package com.kasra.javaee.repository;

import com.kasra.javaee.interfaces.repository.ISickRepository;
import com.kasra.javaee.interfaces.repository.ISickRepositoryRemote;
import com.kasra.javaee.interfaces.repository.IUserRepository;
import com.kasra.javaee.interfaces.repository.IUserRepositoryRemote;
import com.kasra.javaee.model.Sick;
import com.kasra.javaee.model.User;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by kasra.haghpanah on 02/09/2016.
 */
@Stateless
@Local(IUserRepository.class)
@Remote(IUserRepositoryRemote.class)
public class UserRepository implements IUserRepository, IUserRepositoryRemote, Serializable {

    @PersistenceContext(unitName="medical")
    EntityManager em;


    public static String sha256(String password){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        System.out.println("Hex format : " + sb.toString());

        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
        for (int i=0;i<byteData.length;i++) {
            String hex=Integer.toHexString(0xff & byteData[i]);
            if(hex.length()==1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }


    public User save(User entity) {
        entity.setPassword(sha256(entity.getPassword()));
        em.persist(entity);
        return entity;
    }

    public void delete(User entity) {
        em.remove(em.merge(entity));
    }

    public void update(User entity) {
        entity.setPassword(sha256(entity.getPassword()));
        em.merge(entity);
    }

    public List<User> getAll() {

        Query query = em.createNamedQuery(User.GET_ALL);

        try {
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        }

    }

    public User getById(int id) {
        return (User) em.createNamedQuery(User.GET_BY_ID).setParameter("id", id).getSingleResult();
    }

    @Override
    public User getByUsername(String username) {
        return (User) em.createNamedQuery(User.GET_BY_NAME).setParameter("username", username).getSingleResult();
    }


}
