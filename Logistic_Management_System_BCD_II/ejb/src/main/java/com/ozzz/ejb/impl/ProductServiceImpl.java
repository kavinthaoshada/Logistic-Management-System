package com.ozzz.ejb.impl;

import com.ozzz.ejb.entity.Customer;
import com.ozzz.ejb.entity.OrderToManage;
import com.ozzz.ejb.entity.Product;
import com.ozzz.ejb.entity.User;
import com.ozzz.ejb.remote.ProductService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Stateless
public class ProductServiceImpl implements ProductService {
    @PersistenceContext
    EntityManager em;
    @Override
    @RolesAllowed("admin")
    public Product addProduct(String name, String description, int qty, double total_weight, Long orderId) {

            Product product = new Product();
            product.setName(name);

            if(description!=null){
                product.setDescription(description);
            }

            product.setQuantity(qty);
            product.setTotal_weight(total_weight);
            product.setOrder(em.find(OrderToManage.class, orderId));

            em.persist(product);

            return product;
    }

    @Override
    @RolesAllowed({"admin", "employee"})
    public List<Product> getProducts() {
        List<Product> products = em.createQuery("SELECT p FROM Product p",
                Product.class).getResultList();
        return products;
    }
}
