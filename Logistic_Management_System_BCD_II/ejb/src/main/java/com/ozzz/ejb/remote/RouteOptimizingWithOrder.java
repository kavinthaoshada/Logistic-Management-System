package com.ozzz.ejb.remote;

import com.ozzz.ejb.entity.OrderToManage;
import com.ozzz.ejb.entity.Route;
import jakarta.ejb.Remote;
import jakarta.interceptor.InvocationContext;

import java.util.List;

@Remote
public interface RouteOptimizingWithOrder {
    public List<OrderToManage> getOptimizedShipments(List<OrderToManage> allOrderList, Route route);
}
