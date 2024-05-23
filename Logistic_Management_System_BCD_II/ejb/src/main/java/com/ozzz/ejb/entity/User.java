package com.ozzz.ejb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "user_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    @Email
    @Column(nullable = false, unique = true)
    private String email;
    @NotBlank
    @Column(nullable = false, unique = true)
    private String userName;
    @NotBlank
    @Column(nullable = false)
    private String password;
    private String role;
    @NotNull
    @Column(nullable = false)
    private Timestamp registered_date;
    @ManyToOne(cascade = CascadeType.ALL)
    private UserType user_type;
    @ManyToMany
    @JoinTable(
            name = "user_has_orders",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "order_id")
    )
    private List<OrderToManage> order_to_manages = new ArrayList<>();

}
