package com.qa.bugkeeper.repository;

import com.qa.bugkeeper.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
