package com.webjournal.controller.comment;

import com.webjournal.entity.Comment;
import com.webjournal.service.comment.impls.CommentServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class CommentRestController {
    private final CommentServiceImpl service;

    public CommentRestController(CommentServiceImpl service) {
        this.service = service;
    }
}
