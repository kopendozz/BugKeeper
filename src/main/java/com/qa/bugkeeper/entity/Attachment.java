package com.qa.bugkeeper.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

@Getter
@Setter
@Table
@Entity
public class Attachment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false)
    private byte[] attachment;

    public boolean isImage() {
        return StringUtils.containsAnyIgnoreCase(FilenameUtils.getExtension(this.getName()), "jpg", "jpeg");
    }
}
