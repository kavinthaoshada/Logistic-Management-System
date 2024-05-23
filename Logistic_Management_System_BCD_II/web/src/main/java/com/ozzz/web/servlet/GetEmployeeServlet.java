package com.ozzz.web.servlet;

import com.google.gson.Gson;
import com.ozzz.ejb.entity.User;
import com.ozzz.ejb.remote.EmployeeService;
import com.ozzz.ejb.remote.LoginService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

@WebServlet("/getEmployees")
public class GetEmployeeServlet extends HttpServlet {
    @EJB(lookup = "java:global/ear-1.0/com.ozzz-web-1.0/EmployeeServiceImpl")
    private EmployeeService emp;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        List<User> employees = emp.getEmployees();

        JSONArray jsonArray = new JSONArray(employees);

        resp.getWriter().write(jsonArray.toString(4));
    }
}
