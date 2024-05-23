package com.ozzz.ejb.impl;

import com.ozzz.ejb.annotation.CheckOrderValidity;
import com.ozzz.ejb.entity.OrderToManage;
import com.ozzz.ejb.entity.Route;
import com.ozzz.ejb.interceptor.OrderValidationInterceptor;
import com.ozzz.ejb.remote.RouteOptimizingWithOrder;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import jakarta.interceptor.InvocationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Interceptors(OrderValidationInterceptor.class)
@CheckOrderValidity
@Stateless
public class RouteOptimizingWithOrderImpl implements RouteOptimizingWithOrder {

    @Override
    @RolesAllowed("admin")
    public List<OrderToManage> getOptimizedShipments(List<OrderToManage> allOrderList, Route route) {
        List<OrderToManage> optimizedOrderList = validateOrders(allOrderList, route);

        if (optimizedOrderList == null) {
            return Collections.emptyList();
        }else{
            optimizedOrderList = new ArrayList<>();
            for (OrderToManage order : allOrderList) {
                if (isValidOrder(order, route)) {
                        optimizedOrderList.add(order);
                }
            }
        }
        return optimizedOrderList;
    }
    private List<OrderToManage> validateOrders(List<OrderToManage> allOrderList, Route route) {
        return allOrderList;
    }
    private boolean isValidOrder(OrderToManage order, Route route) {
        String customer_address = order.getCustomer_id().getAddress().toLowerCase();
        String route_destination = route.getDestination().toLowerCase();

        return customer_address.contains(route_destination);
    }
}
