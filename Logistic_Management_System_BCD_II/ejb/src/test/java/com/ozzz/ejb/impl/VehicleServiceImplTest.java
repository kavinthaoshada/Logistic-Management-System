package com.ozzz.ejb.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import com.ozzz.ejb.entity.Route;
import com.ozzz.ejb.entity.Vehicle;
import com.ozzz.ejb.entity.VehicleType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {
    @Mock
    private EntityManager em;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle();
        vehicle.setVehicle_id(1L);
        vehicle.setType(VehicleType.TRUCK);
        vehicle.setCapacity(5000.0);
        vehicle.setStatus(true);
    }

    @Test
    void testAddRoute() {
        Vehicle vehicleToPersist = new Vehicle();
        vehicleToPersist.setType(VehicleType.TRUCK);
        vehicleToPersist.setCapacity(5000.0);
        vehicleToPersist.setStatus(true);

        boolean result = vehicleService.addVehicle("TRUCK", 5000.0);

        assertThat(result).isTrue();
        verify(em).persist(any(Vehicle.class));
    }


    @Test
    void testGetVehicleById() {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicle_id(1L);

        TypedQuery<Vehicle> queryMock = mock(TypedQuery.class);
        when(queryMock.setParameter(eq("id"), eq(1L))).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(vehicle);

        when(em.createQuery(anyString(), eq(Vehicle.class))).thenReturn(queryMock);

        Vehicle result = vehicleService.getVehicleById(1L);

        assertThat(result).isEqualTo(vehicle);
    }

    @Test
    void testAddVehicle_InvalidType() {
        assertThatThrownBy(() -> vehicleService.addVehicle("INVALID_TYPE", 1000.0))
                .isInstanceOf(IllegalArgumentException.class);
        verify(em, never()).persist(any(Vehicle.class));
    }

    @Test
    void testGetVehicleById_NotFound() {
        TypedQuery<Vehicle> queryMock = mock(TypedQuery.class);
        when(queryMock.setParameter(eq("id"), eq(999L))).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(NoResultException.class);

        when(em.createQuery(anyString(), eq(Vehicle.class))).thenReturn(queryMock);

        assertThatThrownBy(() -> vehicleService.getVehicleById(999L)).isInstanceOf(NoResultException.class);
    }

    @Test
    void testGetVehicles() {
        List<Vehicle> vehicles = Arrays.asList(new Vehicle(), new Vehicle());

        TypedQuery<Vehicle> queryMock = mock(TypedQuery.class);
        when(queryMock.getResultList()).thenReturn(vehicles);

        when(em.createQuery(anyString(), eq(Vehicle.class))).thenReturn(queryMock);

        List<Vehicle> result = vehicleService.getVehicles();

        assertThat(result).isEqualTo(vehicles);
    }

    @Test
    void testGetVehicles_NoVehiclesExist() {
        TypedQuery<Vehicle> queryMock = mock(TypedQuery.class);
        when(queryMock.getResultList()).thenReturn(Collections.emptyList());

        when(em.createQuery(anyString(), eq(Vehicle.class))).thenReturn(queryMock);

        List<Vehicle> result = vehicleService.getVehicles();

        assertThat(result).isEmpty();
    }

    @Test
    void testUpdateAvailabilityStatus() {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicle_id(1L);
        vehicle.setStatus(true);

        when(em.find(Vehicle.class, 1L)).thenReturn(vehicle);

        boolean result = vehicleService.updateAvailabilityStatus(1L, false);

        assertThat(result).isTrue();
        assertThat(vehicle.getStatus()).isFalse();
        verify(em).merge(vehicle);
    }

    @Test
    void testUpdateAvailabilityStatus_VehicleNotFound() {
        when(em.find(Vehicle.class, 999L)).thenReturn(null);

        boolean result = vehicleService.updateAvailabilityStatus(999L, false);

        assertThat(result).isFalse();
        verify(em, never()).merge(any(Vehicle.class));
    }

    @Test
    void testAddRoutesToVehicle() {
        Route route = new Route();
        List<Route> routes = List.of(route);
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicle_id(1L);
        vehicle.setStatus(true);

        when(em.find(Vehicle.class, 1L)).thenReturn(vehicle);
        EntityTransaction transaction = mock(EntityTransaction.class);
        when(em.getTransaction()).thenReturn(transaction);

        boolean result = vehicleService.addRoutesToVehicle(routes, 1L);

        assertThat(result).isTrue();
        verify(em).merge(vehicle);
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    void testAddRoutesToVehicle_VehicleNotAvailable() {
        Route route = new Route();
        List<Route> routes = List.of(route);
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicle_id(1L);
        vehicle.setStatus(false);

        when(em.find(Vehicle.class, 1L)).thenReturn(vehicle);

        boolean result = vehicleService.addRoutesToVehicle(routes, 1L);

        assertThat(result).isFalse();
        verify(em, never()).merge(vehicle);
    }

    @Test
    void testGetAllVehicleTypes() {
        List<String> vehicleTypes = Arrays.stream(VehicleType.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        List<String> result = vehicleService.getAllVehicleTypes();

        assertThat(result).isEqualTo(vehicleTypes);
    }
}