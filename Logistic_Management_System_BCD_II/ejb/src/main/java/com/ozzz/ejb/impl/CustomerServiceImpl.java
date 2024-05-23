package com.ozzz.ejb.impl;

import com.ozzz.ejb.entity.ActiveStatus;
import com.ozzz.ejb.entity.Customer;
import com.ozzz.ejb.remote.CustomerService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class CustomerServiceImpl implements CustomerService {
    @PersistenceContext
    EntityManager em;

    @Override
    public boolean addCustomer(String name, String address, String contact_number, String email) {

        try {

            Customer isExistCustomer = em.createQuery("SELECT c " +
                            "FROM Customer c WHERE c.email=:email " +
                            "OR c.contact_number=:contact_number", Customer.class)
                    .setParameter("email", email)
                    .setParameter("contact_number", contact_number)
                    .getSingleResult();
            return false;
        } catch (NoResultException e) {
            Customer customer = new Customer();
            customer.setName(name);
            customer.setAddress(address);
            customer.setContact_number(contact_number);
            customer.setEmail(email);
            customer.setStatus(ActiveStatus.ACTIVE);

            System.out.println(customer.toString());

            em.persist(customer);
            return true;
        }
    }

    @Override
    public boolean updateCustomerStatus(String status, Long customer_id) {
        Customer customer = getSelectedCustomer(customer_id);
        if (customer == null) {
            return false;
        }

        switch (status) {
            case "ACTIVE":
            case "PERMANENT_RESTRICTED":
            case "TEMPORARY_RESTRICTED":
                customer.setStatus(ActiveStatus.valueOf(status));
                break;
            default:
                return false;
        }

        try {
            em.merge(customer);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public List<String> getAllStatus() {
        return Arrays.stream(ActiveStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    public List<Customer> getCustomers() {
        List<Customer> customers = em.createQuery("SELECT c " +
                "FROM Customer c", Customer.class).getResultList();
        return customers;
    }

    @Override
    public Customer getSelectedCustomer(Long customer_id) {
        Customer customer = em.find(Customer.class, customer_id);
        if (customer == null) {
            return null;
        }
        return customer;
    }
}
