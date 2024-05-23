package com.ozzz.ejb.impl;

import com.ozzz.ejb.entity.*;
import com.ozzz.ejb.remote.VehicleService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class VehicleServiceImpl implements VehicleService {
    @PersistenceContext
    EntityManager em;
    @Override
    @RolesAllowed("admin")
    public boolean addVehicle(String type, double capacity) {
        Vehicle vehicle = new Vehicle();
        vehicle.setType(VehicleType.valueOf(type));
        vehicle.setCapacity(capacity);
        vehicle.setStatus(true);
        em.persist(vehicle);
        return true;
    }

    @Override
    @RolesAllowed({"admin", "employee"})
    public Vehicle getVehicleById(Long id) {
        Vehicle vehicle = em.createQuery("SELECT v " +
                        "FROM Vehicle v WHERE v.vehicle_id=:id", Vehicle.class)
                .setParameter("id", id)
                .getSingleResult();
        return vehicle;
    }

    @Override
    @RolesAllowed({"admin", "employee"})
    public List<Vehicle> getVehicles() {
        List<Vehicle> vehicles = em.createQuery("SELECT v " +
                "FROM Vehicle v", Vehicle.class).getResultList();
        return vehicles;
    }

    @Override
    @RolesAllowed({"admin", "employee"})
    public boolean updateAvailabilityStatus(long vehicle_id, boolean status) {
        Vehicle vehicle = em.find(Vehicle.class, vehicle_id);
        if (vehicle == null) {
            return false;
        }
        vehicle.setStatus(status);
        try {
            em.merge(vehicle);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    @RolesAllowed("admin")
    public boolean addRoutesToVehicle(List<Route> routes, long vehicle_id) {
        Vehicle vehicle = em.find(Vehicle.class, vehicle_id);
        if(!vehicle.getStatus()){
            return false;
        }
        vehicle.setRoutes(routes);

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(vehicle);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            return false;
        }
    }

    @Override
    @RolesAllowed({"admin", "employee"})
    public List<String> getAllVehicleTypes() {
        return Arrays.stream(VehicleType.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
