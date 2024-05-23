package com.ozzz.ejb.interceptor;

import com.ozzz.ejb.annotation.CheckOrderValidity;
import com.ozzz.ejb.entity.OrderToManage;
import com.ozzz.ejb.entity.Route;
import com.ozzz.ejb.exception.EmptyOptimizeDataListException;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import java.util.ArrayList;
import java.util.List;

@Interceptor
@CheckOrderValidity
public class OrderValidationInterceptor {

    @AroundInvoke
    public Object intercept(InvocationContext ic) throws Exception {

        Object[] parameters = ic.getParameters();

        List<OrderToManage> allOrderList = (List<OrderToManage>) parameters[0];
        Route route = (Route) parameters[1];

        List<OrderToManage> optimizedOrderList = new ArrayList<>();

        for (OrderToManage order : allOrderList) {
            if (isValidOrder(order, route)) {
                    optimizedOrderList.add(order);
            }
        }

        if (optimizedOrderList.size() > 0) {
            return ic.proceed();
        } else {
            throw new EmptyOptimizeDataListException("Empty optimize order List");
        }
    }

    private boolean isValidOrder(OrderToManage order, Route route) {
        String customer_address = order.getCustomer_id().getAddress().trim().toLowerCase();
        String route_destination = route.getDestination().trim().toLowerCase();
        return customer_address.contains(route_destination);
    }
}

