package com.ozzz.web.servlet;

import com.ozzz.ejb.entity.Customer;
import com.ozzz.ejb.entity.Product;
import com.ozzz.ejb.remote.CustomerService;
import com.ozzz.ejb.remote.OrderManageService;
import com.ozzz.ejb.remote.ProductService;
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

@WebServlet("/addShipment")
public class AddShipmentServlet extends HttpServlet {
    @EJB(lookup = "java:global/ear-1.0/com.ozzz-web-1.0/OrderManageServiceImpl")
    private OrderManageService order;

    @EJB(lookup = "java:global/ear-1.0/com.ozzz-web-1.0/ProductServiceImpl!com.ozzz.ejb.remote.ProductService")
    private ProductService productService;

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

        String customerIdStr = jsonData.getString("customer_id");
        Long customerId = Long.valueOf(customerIdStr);

        JSONArray itemsArray = jsonData.getJSONArray("items");

        Long orderId = order.addShipment(customerId);

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < itemsArray.length(); i++) {
            JSONObject itemObj = itemsArray.getJSONObject(i);
            String productName = itemObj.getString("product_name");
            String description = itemObj.getString("description");
            int quantity = itemObj.getInt("quantity");
            double totalWeight = itemObj.getDouble("tot_weight");

            Product product = productService.addProduct(productName, description, quantity, totalWeight, orderId);
            products.add(product);
        }

        resp.setContentType("text/plain");

        if (orderId>0L) {
            resp.getWriter().write("Customer added successfully");
        } else {
            resp.getWriter().write("Something went wrong");
        }
    }
}
