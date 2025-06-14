package com.miniprojecttwo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String userid;

    @NotBlank
    @Size(min = 3, max = 10)
    private String username;

    @NotBlank
    private String password;

//    @ManyToMany(fetch = FetchType.EAGER)
//    private Set<Role> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
//    )
    private List<Role> roles = new ArrayList<>();

}
