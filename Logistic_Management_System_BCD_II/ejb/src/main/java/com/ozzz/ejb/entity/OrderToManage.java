package com.ozzz.ejb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
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
                name = "OrderToManage.findByIdWithRoutes",
                query = "SELECT o FROM OrderToManage o LEFT JOIN FETCH o.routes WHERE o.order_id = :orderId"
        )
})
public class OrderToManage implements Serializable {
    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "order_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long order_id;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;
    @NotNull
    @Column(nullable = false)
    private Timestamp created_at;
    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer_id;
    @ManyToMany(mappedBy = "orderToManages")
    private List<Route> routes = new ArrayList<>();;
    @OneToMany(mappedBy = "order")
    private List<Product> products = new ArrayList<>();
    @ManyToMany(mappedBy = "order_to_manages")
    private List<User> users = new ArrayList<>();

}
