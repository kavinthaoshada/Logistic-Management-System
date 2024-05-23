package com.ozzz.web.servlet;

import com.ozzz.ejb.entity.Customer;
import com.ozzz.ejb.entity.Route;
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

@WebServlet("/getAllRoutes")
public class GetAllRoutesServlet extends HttpServlet {
    @EJB(lookup = "java:global/ear-1.0/com.ozzz-web-1.0/RouteServiceImpl")
    private RouteService routeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<Route> routes = routeService.getRoutes();

        JSONArray jsonArray = new JSONArray(routes);

        resp.getWriter().write(jsonArray.toString(4));
    }
}
