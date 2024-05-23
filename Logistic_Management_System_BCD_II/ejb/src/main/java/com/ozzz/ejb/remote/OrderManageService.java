package com.ozzz.ejb.remote;

import com.ozzz.ejb.entity.Customer;
import com.ozzz.ejb.entity.OrderToManage;
import com.ozzz.ejb.entity.Product;
import com.ozzz.ejb.entity.Route;
import jakarta.ejb.Remote;

import java.sql.Date;
import java.util.List;

@Remote
public interface OrderManageService {
    public List<OrderToManage> getShipments();
    public boolean updateStatus(Long orderId, String status);
    public Long addShipment(Long customer_id);
    public List<Product> getProducts(Long shipment_id);
    public List<String> getAllStatus();
    public OrderToManage getShipmentById(Long id);
    public List<OrderToManage> getShipmentsNotAssigned();
    public List<OrderToManage> getShipmentsAssigned(String email);
    public List<Route> getRouteByShipmentId(Long id);
    public void processRecurringJob();
}
