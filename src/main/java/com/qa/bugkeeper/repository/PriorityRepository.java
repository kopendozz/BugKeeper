package com.qa.bugkeeper.repository;

import com.qa.bugkeeper.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriorityRepository extends JpaRepository<Priority, Long> {

}
