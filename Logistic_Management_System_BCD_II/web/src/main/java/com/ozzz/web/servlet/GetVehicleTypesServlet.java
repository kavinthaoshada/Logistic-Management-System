package com.ozzz.web.servlet;

import com.ozzz.ejb.remote.VehicleService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

@WebServlet("/getVehicleTypesForUpdate")
public class GetVehicleTypesServlet extends HttpServlet {
    @EJB(lookup = "java:global/ear-1.0/com.ozzz-web-1.0/VehicleServiceImpl")
    private VehicleService vehicleService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<String> type_list = vehicleService.getAllVehicleTypes();

        JSONArray jsonArray = new JSONArray(type_list);

        resp.getWriter().write(jsonArray.toString(4));
    }
}
