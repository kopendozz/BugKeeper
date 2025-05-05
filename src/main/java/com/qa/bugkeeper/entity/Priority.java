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
public class Priority implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, length = 45)
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "priority")
    private List<Issue> issues;

    @Transient
    private Integer openIssueCount;
}
