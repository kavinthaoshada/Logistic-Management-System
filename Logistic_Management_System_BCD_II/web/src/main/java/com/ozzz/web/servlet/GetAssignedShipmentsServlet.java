package com.ozzz.web.servlet;

import com.ozzz.ejb.entity.OrderToManage;
import com.ozzz.ejb.remote.OrderManageService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

@WebServlet("/getAllAssignedShipments")
public class GetAssignedShipmentsServlet extends HttpServlet {
    @EJB(lookup = "java:global/ear-1.0/com.ozzz-web-1.0/OrderManageServiceImpl")
    private OrderManageService order;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String employee_email = req.getParameter("email");
        List<OrderToManage> shipments = order.getShipmentsAssigned(employee_email);

        JSONArray jsonArray = new JSONArray(shipments);
        resp.getWriter().write(jsonArray.toString(4));
    }
}
