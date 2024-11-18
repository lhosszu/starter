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

    @With
    @Column(nullable = false)
    private Boolean enabled;

    @Version
    private Long version;

    public static UserBuilder enabled() {
        return builder().enabled(Boolean.TRUE);
    }
}
