package com.ozzz.ejb.impl;

import com.ozzz.ejb.entity.*;
import com.ozzz.ejb.impl.OrderManageServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderManageServiceImplTest {

    @Mock
    private EntityManager em;

    @InjectMocks
    private OrderManageServiceImpl orderManageService;

    private OrderToManage order;
    private User user;

    @BeforeEach
    void setUp() {
        order = new OrderToManage();
        order.setOrder_id(1L);
        order.setStatus(OrderStatus.PENDING);

        user = new User();
        user.setId(1L);
        user.setEmail("john.doe@gmail.com");
    }

    @Test
    void testGetShipments() {
        TypedQuery<OrderToManage> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(OrderToManage.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(order));

        List<OrderToManage> shipments = orderManageService.getShipments();

        assertThat(shipments).hasSize(1);
        assertThat(shipments.get(0)).isEqualTo(order);
    }

    @Test
    void testGetShipmentById() {
        TypedQuery<OrderToManage> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(OrderToManage.class))).thenReturn(query);
        when(query.setParameter("id", 1L)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(order);

        OrderToManage result = orderManageService.getShipmentById(1L);

        assertThat(result).isEqualTo(order);
    }

    @Test
    void testGetShipmentsNotAssigned() {
        OrderToManage order = new OrderToManage();
        order.setUsers(null);

        TypedQuery<OrderToManage> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(OrderToManage.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.singletonList(order));

        List<OrderToManage> shipments = orderManageService.getShipmentsNotAssigned();

        assertThat(shipments).hasSize(1);
        assertThat(shipments.get(0)).isEqualTo(order);
    }

    @Test
    void testGetShipmentsAssigned() {
        TypedQuery<User> userQuery = mock(TypedQuery.class);
        TypedQuery<OrderToManage> orderQuery = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(User.class))).thenReturn(userQuery);
        when(userQuery.setParameter("email", "john.doe@gmail.com")).thenReturn(userQuery);
        when(userQuery.getSingleResult()).thenReturn(user);

        when(em.createQuery(anyString(), eq(OrderToManage.class))).thenReturn(orderQuery);
        when(orderQuery.setParameter("user", user)).thenReturn(orderQuery);
        when(orderQuery.getResultList()).thenReturn(Collections.singletonList(order));

        List<OrderToManage> shipments = orderManageService.getShipmentsAssigned("john.doe@gmail.com");

        assertThat(shipments).hasSize(1);
        assertThat(shipments.get(0)).isEqualTo(order);
    }

    @Test
    void testGetProducts() {
        Product product = new Product();
        product.setName("Test Product");

        order.setProducts(Collections.singletonList(product));

        TypedQuery<OrderToManage> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(OrderToManage.class))).thenReturn(query);
        when(query.setParameter("shipment_id", 1L)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(order);

        List<Product> products = orderManageService.getProducts(1L);

        assertThat(products).hasSize(1);
        assertThat(products.get(0)).isEqualTo(product);
    }

    @Test
    void testGetAllStatus() {
        List<String> statuses = orderManageService.getAllStatus();

        assertThat(statuses).containsExactlyInAnyOrder(
                "PENDING", "PROCESSING", "SHIPPED", "DELIVERED", "CANCELLED", "ON_HOLD",
                "RETURNED", "REFUNDED", "COMPLETED"
        );
    }

    @Test
    void testUpdateStatus() {
        when(em.find(OrderToManage.class, 1L)).thenReturn(order);

        boolean result = orderManageService.updateStatus(1L, "PROCESSING");

        assertThat(result).isTrue();
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PROCESSING);
        verify(em).merge(order);
    }

    @Test
    void testGetRouteByShipmentId() {
        Route route = new Route();
        route.setRoute_id(1L);

        order.setRoutes(Collections.singletonList(route));

        TypedQuery<OrderToManage> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(OrderToManage.class))).thenReturn(query);
        when(query.setParameter("id", 1L)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(order);

        List<Route> routes = orderManageService.getRouteByShipmentId(1L);

        assertThat(routes).hasSize(1);
        assertThat(routes.get(0)).isEqualTo(route);
    }

}