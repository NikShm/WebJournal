package com.webjournal.controller.comment;

import com.webjournal.service.comment.CommentServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class CommentRestController {
    private final CommentServiceImpl service;

    public CommentRestController(CommentServiceImpl service) {
        this.service = service;
    }
}
