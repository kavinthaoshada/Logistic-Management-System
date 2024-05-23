package com.ozzz.web.servlet;

import com.ozzz.ejb.remote.OrderManageService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/updateShipmentStatusFromAdmin")
public class UpdateShipmentStatusFromAdminServlet extends HttpServlet {
    @EJB(lookup = "java:global/ear-1.0/com.ozzz-web-1.0/OrderManageServiceImpl")
    private OrderManageService order;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String shipmentIdStr = req.getParameter("id");
        String status = req.getParameter("status");
        Long shipmentId = Long.parseLong(shipmentIdStr);

        order.updateStatus(shipmentId, status);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("{\"message\": \"Status updated successfully\"}");
    }
}
