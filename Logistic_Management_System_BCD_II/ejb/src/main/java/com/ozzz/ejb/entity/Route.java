package com.ozzz.ejb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table
@NamedQueries({
        @NamedQuery(
                name = "Route.findByIdWithOrders",
                query = "SELECT r FROM Route r LEFT JOIN FETCH r.orderToManages WHERE r.route_id = :routeId"
        )
})
public class Route implements Serializable {
    @Id
    @SequenceGenerator(
            name = "route_sequence",
            sequenceName = "route_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "route_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long route_id;
    @NotBlank
    @Column(nullable = false)
    private String origin;
    @NotBlank
    @Column(nullable = false)
    private String destination;
    @NotNull
    @Column(nullable = false)
    private Double distance;

    @NotNull
    @Column(nullable = false)
    private Timestamp start_date;
    @NotBlank
    @Column(nullable = false)
    private String estimated_time;
    @ManyToMany
    @JoinTable(
            name = "route_has_orders",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private List<OrderToManage> orderToManages = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "route_has_vehicles",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "vehicle_id")
    )
    private List<Vehicle> vehicles = new ArrayList<>();
}
