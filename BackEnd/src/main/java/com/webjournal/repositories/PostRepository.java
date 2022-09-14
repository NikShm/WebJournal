package com.webjournal.repositories;
import com.webjournal.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
@author Микола
@project High_school_library
@class PostRepository
@version 1.0.0
@since 05.09.2022 - 18.53
*/
@Repository
@EnableJpaRepositories("com.webjournal.repositories")
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllById(Integer id);
}
