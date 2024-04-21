package com.qa.bugkeeper.issue.attachment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qa.bugkeeper.issue.Issue;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@Table
@Entity
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @Column(nullable = false, length = 45)
    private String name;

    @JsonIgnore
    @Column(nullable = false)
    private byte[] attachment;

    public boolean isImage() {
        return StringUtils.containsAnyIgnoreCase(FilenameUtils.getExtension(this.getName()), "jpg", "jpeg");
    }
}
