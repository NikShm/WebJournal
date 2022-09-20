package com.webjournal.service.comment.impls;

import com.webjournal.entity.Comment;
import com.webjournal.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl {
    private final CommentRepository repository;

    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }
}
