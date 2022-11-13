package com.webjournal.controller;

import com.webjournal.dto.LikeDTO;
import com.webjournal.dto.PageDTO;
import com.webjournal.dto.PostDTO;
import com.webjournal.dto.search.PostSearch;
import com.webjournal.dto.search.SearchDTO;
import com.webjournal.service.post.PostServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {
    private final PostServiceImpl postService;

    public PostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @PostMapping("/search")
    public PageDTO<PostDTO> showPostPage(@RequestBody SearchDTO<PostSearch> search) {
        return postService.getPage(search);
    }

    @PostMapping("/create")
    public Integer create(@RequestBody PostDTO postDTO) {
        return postService.create(postDTO);
    }

    @PostMapping("/like")
    public void createLike(@RequestBody LikeDTO likeDTO) {
         postService.like(likeDTO);
    }

    @PostMapping("/dislike")
    public void deleteLike(@RequestBody LikeDTO likeDTO) {
        postService.dislike(likeDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public void deleteOne(@PathVariable Integer id) {
        postService.delete(id);
    }

    @PutMapping( "/update/")
    public void update(@RequestBody PostDTO postDTO) {
        postService.update(postDTO);
    }

    @GetMapping("/{id}")
    public PostDTO showOne(@PathVariable Integer id) {
        return postService.get(id);
    }

    @RequestMapping("/")
    List<PostDTO> showAll(){
        return postService.getAll();
    }

    @GetMapping("/top-per-month")
    public List<PostDTO> getFeaturedPosts(@RequestParam("count") int n) {
        LocalDateTime date = LocalDateTime.from(LocalDateTime.now().minusMonths(1));
        return postService.getFeaturedPosts(n, date);
    }
}
