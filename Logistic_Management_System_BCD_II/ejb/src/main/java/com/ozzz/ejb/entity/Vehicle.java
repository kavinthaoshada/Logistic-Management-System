package com.ozzz.ejb.entity;

import jakarta.persistence.*;
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
public class Vehicle implements Serializable {
    @Id
    @SequenceGenerator(
            name = "vehicle_sequence",
            sequenceName = "vehicle_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "vehicle_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long vehicle_id;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType type;
    @NotNull
    @Column(nullable = false)
    private Double capacity;
    @NotNull
    @Column(nullable = false)
    private Boolean status;
    @ManyToMany(mappedBy = "vehicles")
    private List<Route> routes = new ArrayList<>();
}
