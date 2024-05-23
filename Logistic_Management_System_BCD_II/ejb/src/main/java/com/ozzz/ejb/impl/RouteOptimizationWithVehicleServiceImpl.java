package com.ozzz.ejb.impl;

import com.ozzz.ejb.annotation.CheckVehicleValidity;
import com.ozzz.ejb.entity.*;
import com.ozzz.ejb.interceptor.VehicleValidationInterceptor;
import com.ozzz.ejb.remote.RouteOptimizationWithVehicleService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Interceptors(VehicleValidationInterceptor.class)
@CheckVehicleValidity
@Stateless
public class RouteOptimizationWithVehicleServiceImpl implements RouteOptimizationWithVehicleService {
    @PersistenceContext
    EntityManager em;

    @Override
    @RolesAllowed("admin")
    public List<Vehicle> getOptimizedVehicles(Route route) {
        double tot = 0;
        double distance = route.getDistance();

        TypedQuery<Route> query = em.createQuery(
                "SELECT DISTINCT r FROM Route r LEFT JOIN FETCH r.orderToManages WHERE r.route_id = :routeId",
                Route.class
        );
        query.setParameter("routeId", route.getRoute_id());
        List<Route> routes = query.getResultList();

        for (Route fetchedRoute : routes) {
            for (OrderToManage order : fetchedRoute.getOrderToManages()) {
                for (Product p : order.getProducts()) {
                    tot += p.getTotal_weight();
                }
            }
        }

        List<String> vehicleTypeList = new ArrayList<>();

        if (tot <= 15 && distance <= 20) {
            vehicleTypeList.add("MOTOR_CYCLE");
            vehicleTypeList.add("ELECTRIC_SCOOTER");
        } else if (tot <= 75 && distance <= 50) {
            vehicleTypeList.add("VAN");
            vehicleTypeList.add("SUV");
            vehicleTypeList.add("SEDAN");
        } else if (tot <= 200 && distance <= 75) {
            vehicleTypeList.add("VAN");
            vehicleTypeList.add("SUV");
            vehicleTypeList.add("TRUCK");
        } else if (tot > 200 && distance <= 75) {
            vehicleTypeList.add("TRUCK");
        } else if (tot < 200 && distance > 75) {
            vehicleTypeList.add("VAN");
            vehicleTypeList.add("SUV");
        }

        List<VehicleType> enumList = vehicleTypeList.stream()
                .map(VehicleType::valueOf)
                .collect(Collectors.toList());

        List<Vehicle> vehicles = em.createQuery("SELECT v " +
                        "FROM Vehicle v WHERE v.type IN :types AND v.status=true", Vehicle.class)
                .setParameter("types", enumList)
                .getResultList();

        return vehicles;

    }

}
