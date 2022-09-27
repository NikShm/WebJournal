package com.webjournal.controller.post;

import com.webjournal.dto.PostDTO;
import com.webjournal.dto.SearchDTO;
import com.webjournal.entity.Post;
import com.webjournal.enums.SortDirection;
import com.webjournal.services.PostServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/products", produces = "application/json")
public class PostRestController {
    private final PostServiceImpl postService;

    public PostRestController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @GetMapping
    public Page<Post> showAll() {
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearch("roof damage");
        searchDTO.setPage(0);
        searchDTO.setPageSize(2);
        searchDTO.setSortField("title");
        searchDTO.setSortDirection(SortDirection.ASC);
        return postService.getPage(searchDTO);
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
    public List<PostDTO> showInterestingPostsPerMonth(@RequestParam("count") int n) {
        LocalDateTime date = LocalDateTime.from(LocalDateTime.now().minusMonths(1));
        return postService.getInterestingPosts(n, date);
    }
}
