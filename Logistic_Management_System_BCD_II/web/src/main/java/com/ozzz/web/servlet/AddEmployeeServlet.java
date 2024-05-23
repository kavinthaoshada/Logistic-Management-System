package com.ozzz.web.servlet;

import com.ozzz.ejb.remote.EmployeeService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/addEmployees")
public class AddEmployeeServlet extends HttpServlet {
    @EJB(lookup = "java:global/ear-1.0/com.ozzz-web-1.0/EmployeeServiceImpl")
    private EmployeeService emp;
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

        String email = jsonData.getString("email");
        String username = jsonData.getString("username");
        String usertype = jsonData.getString("usertype");
        String password = jsonData.getString("password");
        String user_role = jsonData.getString("user_role");

        boolean isAdd = emp.addEmployee(email, username, password, user_role, usertype);
        resp.setContentType("text/plain");
        if(isAdd){
            resp.getWriter().write("Employee added successfully");
        }else {
            resp.getWriter().write("Something went wrong");
        }
    }
}
