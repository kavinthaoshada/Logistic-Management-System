package com.ozzz.web.servlet;

import com.ozzz.ejb.remote.CustomerService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/addCustomer")
public class AddCustomerServlet extends HttpServlet {
    @EJB(lookup = "java:global/ear-1.0/com.ozzz-web-1.0/CustomerServiceImpl")
    private CustomerService customer;
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

        String name = jsonData.getString("name");
        String address = jsonData.getString("address");
        String contact_number = jsonData.getString("contact_number");
        String email = jsonData.getString("email");

        boolean isAdd = customer.addCustomer(name, address, contact_number, email);
        resp.setContentType("text/plain");

        if(isAdd){
            resp.getWriter().write("Customer added successfully");
        }else {
            resp.getWriter().write("Something went wrong");
        }
    }
}
