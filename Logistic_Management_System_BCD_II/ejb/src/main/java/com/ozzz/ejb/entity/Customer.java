package com.ozzz.ejb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
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
public class Customer implements Serializable {
    @Id
    @SequenceGenerator(
            name = "customer_sequence",
            sequenceName = "customer_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "customer_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long customer_id;
    @NotBlank
    @Column(nullable = false)
    private String name;
    @NotBlank
    @Column(nullable = false)
    private String address;
    @NotBlank
    @Column(nullable = false)
    private String contact_number;
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ActiveStatus status;
    @OneToMany(mappedBy = "customer_id")
    private List<OrderToManage> order_to_manages = new ArrayList<>();
}
