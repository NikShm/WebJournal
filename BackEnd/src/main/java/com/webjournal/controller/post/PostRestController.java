package com.webjournal.controller.post;

import com.webjournal.dto.PostDTO;
import com.webjournal.dto.user.AuthorDTO;
import com.webjournal.entity.Post;
import com.webjournal.service.post.impls.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostRestController {
    @Autowired
    private PostServiceImpl service;

    public PostRestController(PostServiceImpl service) {
        this.service = service;
    }
    @RequestMapping("/")
    List<PostDTO> showAll(){
        return service.getAll();
    }
    @GetMapping("/topPerMonth")
    public List<PostDTO> showInterestingPostsPerMonth(@RequestParam("count") int n) {
        LocalDateTime date = LocalDateTime.from(LocalDateTime.now().minusMonths(1));
        return service.getInterestingPosts(n, date);
    }
}
