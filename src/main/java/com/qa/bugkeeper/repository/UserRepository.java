package com.qa.bugkeeper.repository;

import com.qa.bugkeeper.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {

}
