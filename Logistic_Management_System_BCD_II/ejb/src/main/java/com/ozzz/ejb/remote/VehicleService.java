package com.ozzz.ejb.remote;

import com.ozzz.ejb.entity.Route;
import com.ozzz.ejb.entity.Vehicle;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface VehicleService {
    public boolean addVehicle(String type, double capacity);
    public List<Vehicle> getVehicles();
    public boolean updateAvailabilityStatus(long vehicle_id, boolean status);
    public boolean addRoutesToVehicle(List<Route> routes, long vehicle_id);
    public List<String> getAllVehicleTypes();
    public Vehicle getVehicleById(Long id);
}
