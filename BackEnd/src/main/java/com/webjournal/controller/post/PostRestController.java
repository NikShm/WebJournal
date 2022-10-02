package com.webjournal.controller.post;

import com.webjournal.dto.PostDTO;
import com.webjournal.service.post.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {

    private PostServiceImpl service;

    public PostRestController(PostServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/create/")
    public Integer create(@RequestBody PostDTO postDTO) {
        return service.create(postDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Integer id) throws IOException {
        service.delete(id);
    }

    @PutMapping( "/update/")
    public void update(@RequestBody PostDTO postDTO) throws IOException {
        service.update(postDTO);
    }

    @GetMapping("/{id}")
    public PostDTO showOne(@PathVariable Integer id) {
        return service.get(id);
    }

    @RequestMapping("/")
    List<PostDTO> showAll(){
        return service.getAll();
    }

    @GetMapping("/top-per-month")
    public List<PostDTO> showInterestingPostsPerMonth(@RequestParam("count") int n) {
        LocalDateTime date = LocalDateTime.from(LocalDateTime.now().minusMonths(1));
        return service.getInterestingPosts(n, date);
    }
}
