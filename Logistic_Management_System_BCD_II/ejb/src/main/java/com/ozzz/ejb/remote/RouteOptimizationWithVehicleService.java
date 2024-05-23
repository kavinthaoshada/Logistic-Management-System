package com.ozzz.ejb.remote;

import com.ozzz.ejb.entity.Route;
import com.ozzz.ejb.entity.Vehicle;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface RouteOptimizationWithVehicleService {
    public List<Vehicle> getOptimizedVehicles(Route route);
}
