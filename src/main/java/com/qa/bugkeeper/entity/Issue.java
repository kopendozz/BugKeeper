package com.qa.bugkeeper.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Issue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    @JsonView(value = View.Issues.class)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    @JsonView(value = View.Issues.class)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "priority_id", nullable = false)
    @JsonView(value = View.Issues.class)
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    @JsonView(value = View.Issues.class)
    private Status status;

    @Column(nullable = false, length = 140)
    @JsonView(value = View.Issues.class)
    private String name;

    @Column(nullable = false, length = 5000)
    @JsonView(value = View.Issues.class)
    private String description;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private UserEntity reporter;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    @JsonView(value = View.Issues.class)
    private UserEntity responsible;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    @JsonIgnore
    @OneToMany(mappedBy = "issue", cascade = CascadeType.REMOVE)
    private List<Attachment> attachments;
}
