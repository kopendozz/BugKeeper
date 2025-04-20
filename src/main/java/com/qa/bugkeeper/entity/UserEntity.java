package com.qa.bugkeeper.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    @Id
    @Column(unique = true, nullable = false, length = 45)
    @JsonView(value = {View.Projects.class, View.Users.class, View.Issues.class})
    private String username;

    @Column(nullable = false, length = 45)
    @JsonView(value = {View.Projects.class, View.Users.class})
    private String firstName;

    @Column(nullable = false, length = 45)
    @JsonView(value = {View.Projects.class, View.Users.class})
    private String lastName;

    @Column(unique = true, nullable = false, length = 45)
    @JsonView(value = {View.Projects.class, View.Users.class})
    private String password;

    @Column(nullable = false)
    @JsonView(value = {View.Projects.class, View.Users.class})
    private String role;

    @Column(nullable = false)
    @JsonView(value = {View.Projects.class, View.Users.class})
    private Boolean enabled;

    @JsonIgnore
    @OneToMany(mappedBy = "reporter")
    private List<Issue> issues;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users")
    @JsonView(value = View.Users.class)
    private Set<Project> projects = new HashSet<>();

    public String getFullName() {
        return "%s %s".formatted(firstName, lastName);
    }
}
