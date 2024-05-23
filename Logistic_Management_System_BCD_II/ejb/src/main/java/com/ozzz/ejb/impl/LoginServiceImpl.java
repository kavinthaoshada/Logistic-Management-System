package com.ozzz.ejb.impl;

import com.ozzz.ejb.entity.User;
import com.ozzz.ejb.remote.LoginService;
import jakarta.annotation.security.PermitAll;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class LoginServiceImpl implements LoginService {

    @PersistenceContext
    EntityManager em;

    @Override
    @PermitAll
    public String login(String email, String password) {
        User user = null;
        try {
            user = em.createQuery("SELECT u FROM User u WHERE u.email=:email and u.password=:password", User.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();

            return user.getUser_type().getName();
        } catch (NoResultException e) {
            return "empty";
        }
    }
}
