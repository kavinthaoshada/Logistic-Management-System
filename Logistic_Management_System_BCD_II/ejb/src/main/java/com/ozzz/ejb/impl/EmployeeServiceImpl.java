package com.ozzz.ejb.impl;

import com.ozzz.ejb.entity.*;
import com.ozzz.ejb.remote.EmployeeService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.interceptor.InvocationContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.SystemException;
import jakarta.transaction.Transactional;
import jakarta.transaction.UserTransaction;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Stateless
public class EmployeeServiceImpl implements EmployeeService {
    @PersistenceContext
    private EntityManager em;

    @RolesAllowed("admin")
    @Override
    public List<User> getEmployees() {
        List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
        return users;
    }

    @RolesAllowed("admin")
    @Override
    public boolean addEmployee(String email, String username, String password, String role, String user_type) {

        try {
            User isExistUser = em.createQuery("SELECT u " +
                            "FROM User u WHERE u.email=:email " +
                            "OR u.userName=:userName", User.class)
                    .setParameter("email", email)
                    .setParameter("userName", username)
                    .getSingleResult();
            return false;
        } catch (Exception e) {
            User user = new User();
            user.setEmail(email);
            user.setUserName(username);
            user.setPassword(password);

            if (user_type.equals("admin")) {
                user.setUser_type(em.find(UserType.class, 1L));
            } else if (user_type.equals("employee")) {
                user.setUser_type(em.find(UserType.class, 2L));
            }

            Date currentDate = new Date(System.currentTimeMillis());
            Timestamp currentTimestamp = new Timestamp(currentDate.getTime());
            user.setRegistered_date(currentTimestamp);

            if (role != null) {
                user.setRole(role);
            }
            em.persist(user);

            return true;
        }
    }

    @RolesAllowed("admin")
    @Override
    public boolean updateEmployeeRole(String role, Long employee_id) {
        User user = em.find(User.class, employee_id);
        if (user == null || role == null) {
            return false;
        }

        user.setRole(role);
        try {
            em.merge(user);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    @RolesAllowed({"admin", "employee"})
    public List<UserType> getUserTypes(){
        List<UserType> userTypes = em.createQuery("SELECT ut FROM UserType ut",
                UserType.class).getResultList();
        return userTypes;
    }

    @Override
    @RolesAllowed("admin")
    @Transactional
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean assignOrdersToManage(Long employee_id, List<OrderToManage> orders) {
        try {
            User user = em.find(User.class, employee_id);
            user.setOrder_to_manages(orders);
            em.merge(user);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
