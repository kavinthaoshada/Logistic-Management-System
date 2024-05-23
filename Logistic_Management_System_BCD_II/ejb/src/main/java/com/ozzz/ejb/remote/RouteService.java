package com.ozzz.ejb.remote;

import com.ozzz.ejb.entity.OrderToManage;
import com.ozzz.ejb.entity.Route;
import com.ozzz.ejb.entity.Vehicle;
import jakarta.ejb.Remote;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Remote
public interface RouteService {
    public boolean addRoute(String origin, String destination, double distance, String estimated_time);
    public List<Route> getRoutes();
    public Route getRouteById(Long id);
    public boolean addOrdersToRoute(List<OrderToManage> orders, long route_id);
    public boolean addVehiclesToRoute(List<Vehicle> vehicles, long route_id);
}
