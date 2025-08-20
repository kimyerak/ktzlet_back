package com.kt.backendapp.domain.user;

import com.kt.backendapp.domain.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users",
       indexes = { @Index(name = "idx_users_email", columnList = "email"),
                   @Index(name = "idx_users_role", columnList = "role") })
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255, nullable = false, unique = true)
    private String email;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private UserRole role; // student, teacher

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private UserStatus status; // active, inactive, suspended...
}
