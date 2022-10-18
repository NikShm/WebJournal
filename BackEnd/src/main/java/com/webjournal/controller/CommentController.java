package com.webjournal.controller;

import com.webjournal.service.comment.CommentServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class CommentController {
    private final CommentServiceImpl service;

    public CommentController(CommentServiceImpl service) {
        this.service = service;
    }
}
