package com.webjournal.repositories;
import com.webjournal.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
/*
@author Микола
@project High_school_library
@class UserRepository
@version 1.0.0
@since 05.09.2022 - 18.52
*/
@Repository
@EnableJpaRepositories("com.webjournal.repositories")
public interface UserRepository extends JpaRepository<Author, Integer>, JpaSpecificationExecutor<Author> {
}
