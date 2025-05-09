package com.qa.bugkeeper.repository;

import com.qa.bugkeeper.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssueRepository extends JpaRepository<Issue, Long> {

}
