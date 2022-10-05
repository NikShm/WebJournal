package com.webjournal.controller.post;

import com.webjournal.dto.PageDTO;
import com.webjournal.dto.PostDTO;
import com.webjournal.dto.SearchDTO;
import com.webjournal.service.post.PostServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostRestController {
    private final PostServiceImpl postService;

    public PostRestController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @PostMapping("/search")
    public PageDTO<PostDTO> showPostPage(@RequestBody SearchDTO search) {
        return postService.getPage(search);
    }

    @PostMapping("/create/")
    public Integer create(@RequestBody PostDTO postDTO) {
        return postService.create(postDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Integer id) throws IOException {
        postService.delete(id);
    }

    @PutMapping( "/update/")
    public void update(@RequestBody PostDTO postDTO) throws IOException {
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
