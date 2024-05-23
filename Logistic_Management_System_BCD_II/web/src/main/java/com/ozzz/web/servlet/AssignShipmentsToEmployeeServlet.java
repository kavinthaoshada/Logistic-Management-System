package com.ozzz.web.servlet;

import com.ozzz.ejb.entity.OrderToManage;
import com.ozzz.ejb.entity.Product;
import com.ozzz.ejb.remote.EmployeeService;
import com.ozzz.ejb.remote.OrderManageService;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/assignShipmentsToEmployee")
public class AssignShipmentsToEmployeeServlet extends HttpServlet {
    @EJB(lookup = "java:global/ear-1.0/com.ozzz-web-1.0/EmployeeServiceImpl")
    private EmployeeService emp;
    @EJB(lookup = "java:global/ear-1.0/com.ozzz-web-1.0/OrderManageServiceImpl")
    private OrderManageService order;
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
        Long employeeId = jsonData.getLong("employeeId");
        JSONArray checkedOrderIdsArray = jsonData.getJSONArray("checkedOrderIds");

        List<OrderToManage> orderToManages = new ArrayList<>();

        for (int i = 0; i < checkedOrderIdsArray.length(); i++) {
            Long orderId = checkedOrderIdsArray.getLong(i);
            OrderToManage orderToManage = order.getShipmentById(orderId);
            orderToManages.add(orderToManage);
        }
        boolean isAdd = emp.assignOrdersToManage(employeeId, orderToManages);

        resp.setContentType("text/plain");

        if (isAdd) {
            resp.getWriter().write("Customer added successfully");
        } else {
            resp.getWriter().write("Something went wrong");
        }

    }
}
