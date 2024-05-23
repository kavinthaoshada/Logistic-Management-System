package com.ozzz.web.servlet;

import com.ozzz.ejb.remote.VehicleService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/addVehicle")
public class AddVehicleServlet extends HttpServlet {
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

        String vehicle_type = jsonData.getString("vehicle_type");
        double capacity = jsonData.getDouble("capacity");

        boolean isAdd = vehicleService.addVehicle(vehicle_type, capacity);
        resp.setContentType("text/plain");

        if(isAdd){
            resp.getWriter().write("Customer added successfully");
        }else {
            resp.getWriter().write("Something went wrong");
        }
    }
}
