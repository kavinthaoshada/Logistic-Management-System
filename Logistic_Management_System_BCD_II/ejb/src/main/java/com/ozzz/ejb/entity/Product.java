package com.ozzz.ejb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table
public class Product implements Serializable {
    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "product_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long product_id;
    @NotBlank
    @Column(nullable = false)
    private String name;
    private String description;
    @NotNull
    @Column(nullable = false)
    private Integer quantity;
    @NotNull
    @Column(nullable = false)
    private Double total_weight;
    @ManyToOne(cascade = CascadeType.ALL)
    private OrderToManage order;

    public Product(String name, String description, Integer quantity, Double total_weight) {
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.total_weight = total_weight;
    }
}
