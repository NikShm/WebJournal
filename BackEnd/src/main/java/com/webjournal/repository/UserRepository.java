package com.webjournal.repository;

import com.webjournal.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
@author Микола
@project High_school_library
@class UserRepository
@version 1.0.0
@since 05.09.2022 - 18.52
*/
@Repository
@EnableJpaRepositories("com.webjournal.repository")
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    @Query("SELECT u FROM User u ORDER BY u.followers.size DESC")
    List<User> findTopBloggers(Pageable page);
}
