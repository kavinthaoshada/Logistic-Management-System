package com.ozzz.web.servlet;

import com.ozzz.ejb.entity.OrderToManage;
import com.ozzz.ejb.entity.Product;
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

@WebServlet("/getProductsByShipmentId")
public class GetProductsByShipmentServlet extends HttpServlet {
    @EJB(lookup = "java:global/ear-1.0/com.ozzz-web-1.0/OrderManageServiceImpl")
    private OrderManageService order;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String shipmentIdStr = req.getParameter("id");
        Long shipmentId = Long.parseLong(shipmentIdStr);

        List<Product> products = order.getProducts(shipmentId);

//        for(Product p : products){
//            System.out.println("pp : "+p.getName());
//        }

        JSONArray jsonArray = new JSONArray(products);

        resp.getWriter().write(jsonArray.toString(4));
    }
}
