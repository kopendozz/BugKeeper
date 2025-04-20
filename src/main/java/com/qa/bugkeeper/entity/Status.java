package com.qa.bugkeeper.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Table
@Entity
public class Status implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    @JsonView(value = View.Issues.class)
    private Long id;

    @Column(nullable = false, length = 45)
    @JsonView(value = View.Issues.class)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "status")
    private List<Issue> issues;

    @JsonIgnore
    @Transient
    private Integer openIssueCount;
}
