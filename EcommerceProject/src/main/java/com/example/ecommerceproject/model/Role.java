package com.example.ecommerceproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    @NotEmpty
    @Column(name = "roles_name" , nullable = false)
    private String name;

    @ManyToMany(mappedBy = "role")
    private List<User> users;
}
