package com.qa.bugkeeper.repository;

import com.qa.bugkeeper.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {

}
