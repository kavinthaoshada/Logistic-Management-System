package com.ozzz.web.servlet;

import com.ozzz.ejb.remote.EmployeeService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/updateEmployeeRole")
public class UpdateUserRoleServlet extends HttpServlet {
    @EJB(lookup = "java:global/ear-1.0/com.ozzz-web-1.0/EmployeeServiceImpl")
    private EmployeeService emp;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String empIdStr = req.getParameter("id");
        String role = req.getParameter("role");
        Long empId = Long.parseLong(empIdStr);

        emp.updateEmployeeRole(role, empId);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write("{\"message\": \"Role updated successfully\"}");
    }
}
