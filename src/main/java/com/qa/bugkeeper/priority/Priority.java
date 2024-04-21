package com.qa.bugkeeper.priority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.qa.bugkeeper.project.View;
import com.qa.bugkeeper.issue.Issue;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Table
@Entity
public class Priority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    @JsonView(value = View.Issues.class)
    private Long id;

    @Column(nullable = false, length = 45)
    @JsonView(value = View.Issues.class)
    private String name;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "priority")
    private List<Issue> issues;

    @JsonIgnore
    @Transient
    private Integer openIssueCount;
}
