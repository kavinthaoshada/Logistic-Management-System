package com.ozzz.web.servlet;

import com.ozzz.ejb.entity.OrderToManage;
import com.ozzz.ejb.entity.Route;
import com.ozzz.ejb.remote.OrderManageService;
import com.ozzz.ejb.remote.RouteOptimizingWithOrder;
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

@WebServlet("/getOptimizedShipments")
public class GetOptimizedShipmentsServlet extends HttpServlet {
    @EJB(lookup = "java:global/ear-1.0/com.ozzz-web-1.0/RouteOptimizingWithOrderImpl")
    private RouteOptimizingWithOrder routeOptimizingWithOrder;

    @EJB(lookup = "java:global/ear-1.0/com.ozzz-web-1.0/RouteServiceImpl")
    private RouteService routeService;

    @EJB(lookup = "java:global/ear-1.0/com.ozzz-web-1.0/OrderManageServiceImpl")
    private OrderManageService order;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String route_id_str = req.getParameter("id");
        Long route_id = Long.parseLong(route_id_str);

        Route route = routeService.getRouteById(route_id);

        List<OrderToManage> orderToManages = order.getShipments();

        List<OrderToManage> optimizedOrderToManages =
                routeOptimizingWithOrder.getOptimizedShipments(orderToManages, route);

        JSONArray jsonArray = new JSONArray(optimizedOrderToManages);

        resp.getWriter().write(jsonArray.toString(4));
    }
}
