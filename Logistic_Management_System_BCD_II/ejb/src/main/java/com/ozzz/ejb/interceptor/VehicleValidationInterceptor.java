package com.ozzz.ejb.interceptor;

import com.ozzz.ejb.annotation.CheckVehicleValidity;
import com.ozzz.ejb.entity.OrderToManage;
import com.ozzz.ejb.entity.Product;
import com.ozzz.ejb.entity.Route;
import com.ozzz.ejb.exception.IncorrectRouteDataException;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

@Interceptor
@CheckVehicleValidity
public class VehicleValidationInterceptor {
    @PersistenceContext
    private EntityManager em;
    @AroundInvoke
    public Object intercept(InvocationContext ic) throws Exception {

        Object[] parameters = ic.getParameters();

        Route route = (Route) parameters[0];

        double tot = 0;


        TypedQuery<Route> query = em.createQuery(
                "SELECT DISTINCT r FROM Route r LEFT JOIN FETCH r.orderToManages WHERE r.route_id = :routeId",
                Route.class
        );
        query.setParameter("routeId", route.getRoute_id());
        List<Route> routes = query.getResultList();

        for (Route fetchedRoute : routes) {
            for (OrderToManage order : fetchedRoute.getOrderToManages()) {
                for (Product p : order.getProducts()) {
                    tot += p.getTotal_weight();
                }
            }
        }

        if (tot > 0 && route.getDistance()>10) {
            return ic.proceed();
        } else {
            throw new IncorrectRouteDataException("Incorrect Route Data!");
        }
    }
}
