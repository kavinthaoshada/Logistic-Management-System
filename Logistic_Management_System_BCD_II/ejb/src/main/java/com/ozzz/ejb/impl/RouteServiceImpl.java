package com.ozzz.ejb.impl;

import com.ozzz.ejb.entity.OrderToManage;
import com.ozzz.ejb.entity.Route;
import com.ozzz.ejb.entity.Vehicle;
import com.ozzz.ejb.remote.RouteService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Stateless
public class RouteServiceImpl implements RouteService {
    @PersistenceContext
    EntityManager em;
    @Override
    @RolesAllowed("admin")
    public boolean addRoute(String origin, String destination, double distance, String estimated_time) {

        Route route = new Route();
        route.setOrigin(origin);
        route.setDestination(destination);
        route.setDistance(distance);
        route.setEstimated_time(estimated_time);

        Date currentDate = new Date(System.currentTimeMillis());
        Timestamp currentTimestamp = new Timestamp(currentDate.getTime());
        route.setStart_date(currentTimestamp);

        em.persist(route);
        return true;
    }

    @Override
    @RolesAllowed({"admin", "employee"})
    public List<Route> getRoutes() {
        List<Route> routes = em.createQuery("SELECT o " +
                "FROM Route o", Route.class).getResultList();
        return routes;
    }

    @Override
    @RolesAllowed({"admin", "employee"})
    public Route getRouteById(Long id) {
        Route route = em.createQuery("SELECT o " +
                "FROM Route o WHERE o.route_id=:id", Route.class)
                .setParameter("id", id)
                .getSingleResult();
        return route;
    }

    @Override
    @RolesAllowed("admin")
    public boolean addOrdersToRoute(List<OrderToManage> orders, long route_id) {
        Route route = em.find(Route.class, route_id);
        if(route==null){
            return false;
        }
        route.setOrderToManages(orders);
        try {
            em.merge(route);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    @RolesAllowed("admin")
    public boolean addVehiclesToRoute(List<Vehicle> vehicles, long route_id) {
        Route route = em.find(Route.class, route_id);
        if(route==null){
            return false;
        }
        route.setVehicles(vehicles);
        try {
            em.merge(route);
            for(Vehicle v : vehicles) {
                Vehicle vehicle = em.find(Vehicle.class, v.getVehicle_id());
                vehicle.setStatus(false);
                try {
                    em.merge(vehicle);
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
