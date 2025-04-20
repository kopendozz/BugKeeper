package com.qa.bugkeeper.repository;

import com.qa.bugkeeper.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

}
