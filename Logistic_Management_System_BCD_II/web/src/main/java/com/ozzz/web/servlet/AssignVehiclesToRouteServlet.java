package com.ozzz.web.servlet;

import com.ozzz.ejb.entity.OrderToManage;
import com.ozzz.ejb.entity.Vehicle;
import com.ozzz.ejb.remote.RouteService;
import com.ozzz.ejb.remote.VehicleService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/assignVehiclesToRoute")
public class AssignVehiclesToRouteServlet extends HttpServlet {
    @EJB(lookup = "java:global/ear-1.0/com.ozzz-web-1.0/RouteServiceImpl")
    private RouteService routeService;
    @EJB(lookup = "java:global/ear-1.0/com.ozzz-web-1.0/VehicleServiceImpl")
    private VehicleService vehicleService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        reader.close();

        JSONObject jsonData = new JSONObject(sb.toString());
        Long route_id = jsonData.getLong("route_id");
        JSONArray checkedVehicleIdsArray = jsonData.getJSONArray("checkedVehicleIds");

        List<Vehicle> vehicles = new ArrayList<>();
        for (int i = 0; i < checkedVehicleIdsArray.length(); i++) {
            Long vehicleId = checkedVehicleIdsArray.getLong(i);
            Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
            vehicles.add(vehicle);
        }
        boolean isAdd = routeService.addVehiclesToRoute(vehicles, route_id);

        resp.setContentType("text/plain");

        if (isAdd) {
            resp.getWriter().write("Vehicles added to route successfully");
        } else {
            resp.getWriter().write("Something went wrong");
        }
    }
}
