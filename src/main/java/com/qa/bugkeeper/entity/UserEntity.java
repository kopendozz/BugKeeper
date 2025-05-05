package com.qa.bugkeeper.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity implements Serializable {

    @Id
    @Column(unique = true, nullable = false, length = 45)
    private String username;

    @Column(nullable = false, length = 45)
    private String firstName;

    @Column(nullable = false, length = 45)
    private String lastName;

    @Column(unique = true, nullable = false, length = 45)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private Boolean enabled;

    @OneToMany(mappedBy = "reporter")
    private List<Issue> issues;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private Set<Project> projects = new HashSet<>();
}
