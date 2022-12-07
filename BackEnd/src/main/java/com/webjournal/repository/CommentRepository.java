package com.webjournal.repository;

import com.webjournal.entity.Comment;
import com.webjournal.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class CommentRepository
 * @since 9/20/2022 - 16.35
 **/
@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>, JpaSpecificationExecutor<Comment> {
}
