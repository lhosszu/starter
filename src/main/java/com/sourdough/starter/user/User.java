package com.sourdough.starter.user;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @With
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean enabled;
}
