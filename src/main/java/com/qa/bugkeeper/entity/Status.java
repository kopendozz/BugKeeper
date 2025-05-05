package com.qa.bugkeeper.entity;

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
    private Long id;

    @Column(nullable = false, length = 45)
    private String name;

    @OneToMany(mappedBy = "status")
    private List<Issue> issues;

    @Transient
    private Integer openIssueCount;
}
