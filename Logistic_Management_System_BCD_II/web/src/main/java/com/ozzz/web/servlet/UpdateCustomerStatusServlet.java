package com.ozzz.web.servlet;

import com.ozzz.ejb.remote.CustomerService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/updateCustomerStatus")
public class UpdateCustomerStatusServlet extends HttpServlet {
    @EJB(lookup = "java:global/ear-1.0/com.ozzz-web-1.0/CustomerServiceImpl")
    private CustomerService customer;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String customerIdStr = req.getParameter("id");
        String status = req.getParameter("status");
        Long customerId = Long.parseLong(customerIdStr);

        customer.updateCustomerStatus(status, customerId);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("{\"message\": \"Status updated successfully\"}");
    }
}
