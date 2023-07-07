package com.example.ecommerceproject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "web_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

    @NotEmpty
    @Column(name = "first_name" , nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @NotEmpty
    @Column(name = "user_email" , nullable = false)
    @Email(message = "{errors.invalid_email}")
    private String email;

    @Column(name = "user_password")
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE , fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles" ,
            joinColumns = {@JoinColumn(name = "USER_ID" , referencedColumnName = "ID") },
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID" , referencedColumnName = "ID")}
    )
    @Column(name = "role")
    private List<Role> role;

    public User(User user){
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.Id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    public User() {

    }
}
