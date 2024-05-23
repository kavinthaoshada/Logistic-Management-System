package com.ozzz.web.servlet;

import com.ozzz.ejb.remote.RouteService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/addRoute")
public class AddRouteServlet extends HttpServlet {
    @EJB(lookup = "java:global/ear-1.0/com.ozzz-web-1.0/RouteServiceImpl")
    private RouteService routeService;
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

        String origin = jsonData.getString("origin");
        String destination = jsonData.getString("destination");
        double distance = jsonData.getDouble("distance");

        String estimated_time_str = jsonData.getString("estimated_time");

        boolean isAdd = routeService.addRoute(origin, destination, distance, estimated_time_str);
        resp.setContentType("text/plain");

        if(isAdd){
            resp.getWriter().write("Route added successfully");
        }else {
            resp.getWriter().write("Something went wrong");
        }
    }
}
