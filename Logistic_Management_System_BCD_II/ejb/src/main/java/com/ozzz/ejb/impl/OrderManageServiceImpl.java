package com.ozzz.ejb.impl;

import com.ozzz.ejb.entity.*;
import com.ozzz.ejb.remote.OrderManageService;
import com.ozzz.ejb.remote.ProductService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Stateless
public class OrderManageServiceImpl implements OrderManageService {
    @PersistenceContext
    EntityManager em;

    @Override
    @RolesAllowed({"admin", "employee"})
    public List<OrderToManage> getShipments() {
        List<OrderToManage> shipments = em.createQuery("SELECT o " +
                "FROM OrderToManage o", OrderToManage.class).getResultList();
        return shipments;
    }

    @Override
    @RolesAllowed({"admin", "employee"})
    public OrderToManage getShipmentById(Long id) {
        OrderToManage shipment = em.createQuery("SELECT o " +
                        "FROM OrderToManage o WHERE o.order_id=:id", OrderToManage.class)
                .setParameter("id", id)
                .getSingleResult();
        return shipment;
    }

    @Override
    @RolesAllowed("admin")
    public List<OrderToManage> getShipmentsNotAssigned() {
        List<OrderToManage> shipments = em.createQuery("SELECT o " +
                "FROM OrderToManage o", OrderToManage.class).getResultList();
        List<OrderToManage> filtered_shipments = new ArrayList<>();
        for (OrderToManage s : shipments) {
            if (s.getUsers() == null) {
                filtered_shipments.add(s);
            }
        }
        return filtered_shipments;
    }

    @Override
    @RolesAllowed({"admin", "employee"})
    public List<OrderToManage> getShipmentsAssigned(String email) {
        User user = em.createQuery("SELECT u FROM User u WHERE u.email=:email", User.class)
                .setParameter("email", email)
                .getSingleResult();

        List<OrderToManage> shipments = em.createQuery("SELECT o " +
                "FROM OrderToManage o WHERE o.users=:user", OrderToManage.class)
                .setParameter("user", user)
                .getResultList();

        List<OrderToManage> filtered_shipments = new ArrayList<>();
        for (OrderToManage s : shipments) {
            filtered_shipments.add(s);
        }
        return filtered_shipments;
    }

    @Override
    @RolesAllowed({"admin", "employee"})
    public List<Product> getProducts(Long shipment_id) {
        System.out.println("in gp");
        System.out.println("id : "+shipment_id);
        OrderToManage shipment = em.createQuery("SELECT o " +
                        "FROM OrderToManage o WHERE o.order_id=:shipment_id", OrderToManage.class)
                .setParameter("shipment_id", shipment_id)
                .getSingleResult();
        List<Product> products = shipment.getProducts();
        for (Product p : products){
            System.out.println("getP : "+p.getName());
        }
        return products;
    }

    @Override
    @RolesAllowed({"admin", "employee"})
    public List<String> getAllStatus() {
        return Arrays.stream(OrderStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Override
    @RolesAllowed({"admin", "employee"})
    public boolean updateStatus(Long orderId, String status) {

        OrderToManage order = em.find(OrderToManage.class, orderId);
        if (order == null) {
            return false;
        }

        System.out.println("stat : " + status);

        switch (status) {
            case "PENDING":
            case "PROCESSING":
            case "SHIPPED":
            case "DELIVERED":
            case "CANCELLED":
            case "ON_HOLD":
            case "RETURNED":
            case "REFUNDED":
            case "COMPLETED":
                order.setStatus(OrderStatus.valueOf(status));
                break;
            default:
                return false;
        }

        try {
            em.merge(order);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    @Override
    @RolesAllowed("admin")
    public Long addShipment(Long customer_id) {

        OrderToManage orderToManage = new OrderToManage();
        orderToManage.setCustomer_id(em.find(Customer.class, customer_id));
        orderToManage.setStatus(OrderStatus.PENDING);

        Date currentDate = new Date(System.currentTimeMillis());
        Timestamp currentTimestamp = new Timestamp(currentDate.getTime());
        orderToManage.setCreated_at(currentTimestamp);

        try {
            em.persist(orderToManage);
            em.flush();
            em.refresh(orderToManage);
            Long orderId = orderToManage.getOrder_id();
            return orderId;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    @Override
    public List<Route> getRouteByShipmentId(Long id){
        OrderToManage order = em.createQuery("SELECT o " +
                        "FROM OrderToManage o WHERE o.order_id=:id", OrderToManage.class)
                .setParameter("id", id)
                .getSingleResult();

        List<Route> routes = order.getRoutes();
        return routes;
    }

    private static final Logger logger = Logger.getLogger(OrderManageServiceImpl.class.getName());
    public void processRecurringJob() {
        LocalTime currentTime = LocalTime.now(ZoneId.systemDefault());
        if (currentTime.getHour() == 18 && currentTime.getMinute() == 0) {
            String logMessage = "Recurring job executed at 6:00 PM.";
            logger.info(logMessage);

        }
    }
}
