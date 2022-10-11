package com.webjournal.controller;

import com.webjournal.dto.CommentDTO;
import com.webjournal.dto.PageDTO;
import com.webjournal.dto.search.SearchAuthorDTO;
import com.webjournal.dto.search.SearchCommentDTO;
import com.webjournal.dto.user.AuthorDTO;
import com.webjournal.dto.user.UserDTO;
import com.webjournal.entity.Comment;
import com.webjournal.service.comment.CommentServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/comments", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentRestController {
    private final CommentServiceImpl service;

    public CommentController(CommentServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/create/")
    public Integer create(@RequestBody CommentDTO commentDTO) {
        return service.create(commentDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Integer id) throws IOException {
        service.delete(id);
    }

    @PutMapping( "/update/")
    public void update(@RequestBody CommentDTO commentDTO) throws IOException {
        service.update(commentDTO);
    }

    @PostMapping("/search")
    public PageDTO<CommentDTO> getPage(@RequestBody SearchCommentDTO search) {
        return service.getPage(search);
    }
}
