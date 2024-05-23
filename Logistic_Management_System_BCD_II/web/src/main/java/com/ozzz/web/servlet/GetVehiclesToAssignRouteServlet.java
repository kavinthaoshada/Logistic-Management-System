package com.ozzz.web.servlet;

import com.ozzz.ejb.entity.OrderToManage;
import com.ozzz.ejb.entity.Route;
import com.ozzz.ejb.entity.Vehicle;
import com.ozzz.ejb.remote.RouteOptimizationWithVehicleService;
import com.ozzz.ejb.remote.RouteService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

@WebServlet("/getVehiclesToAssign")
public class GetVehiclesToAssignRouteServlet extends HttpServlet {
    @EJB(lookup = "java:global/ear-1.0/com.ozzz-web-1.0/RouteServiceImpl")
    private RouteService routeService;

    @EJB(lookup = "java:global/ear-1.0/com.ozzz-web-1.0/RouteOptimizationWithVehicleServiceImpl!com.ozzz.ejb.remote.RouteOptimizationWithVehicleService")
    private RouteOptimizationWithVehicleService routeOptimization;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String route_id_str = req.getParameter("id");
        Long route_id = Long.parseLong(route_id_str);

        Route route = routeService.getRouteById(route_id);

        List<Vehicle> optimizedVehicles =
                routeOptimization.getOptimizedVehicles(route);

        JSONArray jsonArray = new JSONArray(optimizedVehicles);

        resp.getWriter().write(jsonArray.toString(4));
    }
}
