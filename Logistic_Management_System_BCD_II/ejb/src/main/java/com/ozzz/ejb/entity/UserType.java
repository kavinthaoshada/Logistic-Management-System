package com.ozzz.ejb.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "user_type")
public class UserType implements Serializable {
    @Id
    @SequenceGenerator(
            name = "user_type_sequence",
            sequenceName = "user_type_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "user_type_sequence",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "user_type")
    private List<User> users = new ArrayList<>();
}
