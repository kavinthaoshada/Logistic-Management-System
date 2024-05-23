package com.ozzz.ejb.impl;

import com.ozzz.ejb.entity.OrderToManage;
import com.ozzz.ejb.entity.Route;
import com.ozzz.ejb.entity.Vehicle;
import com.ozzz.ejb.impl.RouteServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RouteServiceImplTest {
    @Mock
    private EntityManager em;

    @InjectMocks
    private RouteServiceImpl routeService;

    private Route route;

    @BeforeEach
    void setUp() {
        route = new Route();
        route.setRoute_id(1L);
        route.setOrigin("City A");
        route.setDestination("City B");
        route.setDistance(100.0);
        route.setEstimated_time("2 hours");
        route.setStart_date(new Timestamp(System.currentTimeMillis()));
    }

    @Test
    void testAddRoute() {
        Route routeToPersist = new Route();
        routeToPersist.setOrigin("City A");
        routeToPersist.setDestination("City B");
        routeToPersist.setDistance(100.0);
        routeToPersist.setEstimated_time("2 hours");

        boolean result = routeService.addRoute("City A", "City B", 100.0, "2 hours");

        assertThat(result).isTrue();
        verify(em).persist(any(Route.class));
    }

    @Test
    void testGetRoutesWhenNoRoutesExist() {
        TypedQuery<Route> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Route.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(Collections.emptyList());

        List<Route> routes = routeService.getRoutes();

        assertThat(routes).isEmpty();
    }

    @Test
    void testGetRoutesWhenRoutesExist() {
        TypedQuery<Route> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Route.class))).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(route));

        List<Route> routes = routeService.getRoutes();

        assertThat(routes).hasSize(1);
        assertThat(routes.get(0)).isEqualTo(route);
    }

    @Test
    void testGetRouteById() {
        TypedQuery<Route> query = mock(TypedQuery.class);
        when(em.createQuery(anyString(), eq(Route.class))).thenReturn(query);
        when(query.setParameter("id", 1L)).thenReturn(query);
        when(query.getSingleResult()).thenReturn(route);

        Route result = routeService.getRouteById(1L);

        assertThat(result).isEqualTo(route);
    }

    @Test
    void testAddOrdersToRoute() {
        when(em.find(Route.class, 1L)).thenReturn(route);

        List<OrderToManage> orders = List.of(new OrderToManage());
        boolean result = routeService.addOrdersToRoute(orders, 1L);

        assertThat(result).isTrue();
        verify(em).merge(route);
    }

    @Test
    void testAddOrdersToRouteWhenRouteDoesNotExist() {
        when(em.find(Route.class, 1L)).thenReturn(null);

        List<OrderToManage> orders = List.of(new OrderToManage());
        boolean result = routeService.addOrdersToRoute(orders, 1L);

        assertThat(result).isFalse();
        verify(em, never()).merge(any(Route.class));
    }

    @Test
    void testAddVehiclesToRoute() {
        when(em.find(Route.class, 1L)).thenReturn(route);

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicle_id(1L);
        vehicle.setStatus(true);

        when(em.find(Vehicle.class, 1L)).thenReturn(vehicle);

        List<Vehicle> vehicles = List.of(vehicle);
        boolean result = routeService.addVehiclesToRoute(vehicles, 1L);

        assertThat(result).isTrue();
        verify(em).merge(route);
        verify(em).merge(vehicle);
        assertThat(vehicle.getStatus()).isFalse();
    }

    @Test
    void testAddVehiclesToRouteWhenRouteDoesNotExist() {
        when(em.find(Route.class, 1L)).thenReturn(null);

        Vehicle vehicle = new Vehicle();
        List<Vehicle> vehicles = List.of(vehicle);
        boolean result = routeService.addVehiclesToRoute(vehicles, 1L);

        assertThat(result).isFalse();
        verify(em, never()).merge(any(Route.class));
    }
}