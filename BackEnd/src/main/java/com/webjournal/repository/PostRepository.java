package com.webjournal.repository;

import com.webjournal.entity.Post;
import com.webjournal.entity.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.time.LocalDateTime;
import java.util.List;

/*
@author Емілія
@project WebJournal
@class UserRepository
@version 1.0.0
@since 28.09.2022 - 18.52
*/
@Repository
public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {
    @Query("SELECT u FROM Post u WHERE u.publishedAt BETWEEN ?1 AND current_date ORDER BY u.likes DESC")
    List<Post> findInterestingPosts(Pageable page, LocalDateTime date);
    @Query(value = "SELECT * FROM post p left join follow folow on folow.user_id = ?1 where p.author_id = following_user_id and is_approved = true",
            nativeQuery = true)
    List<Post> findNewsPosts(Pageable page, Integer authorizeUserId);
}
