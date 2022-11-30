package com.webjournal.controller;

import com.webjournal.dto.*;
import com.webjournal.dto.LikeDTO;
import com.webjournal.dto.PageDTO;
import com.webjournal.dto.PostDTO;
import com.webjournal.dto.search.PostSearch;
import com.webjournal.dto.search.SearchDTO;
import com.webjournal.service.fileStorage.FilesStorageServiceImpl;
import com.webjournal.service.post.PostServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping(value = "/api/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {
    private final PostServiceImpl postService;
    private final FilesStorageServiceImpl fileService;

    public PostController(PostServiceImpl postService, FilesStorageServiceImpl fileService) {
        this.postService = postService;
        this.fileService = fileService;
    }

    @PostMapping("/search")
    public PageDTO<PostDTO> showPostPage(@RequestBody SearchDTO<PostSearch> search) {
        return postService.getPage(search);
    }

    @PostMapping("/create")
    public Integer create(@RequestBody PostFormDTO postDTO) {
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
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR', 'AUTHOR')")
    public void deleteOne(@PathVariable Integer id) throws IOException {
        postService.delete(id);
    }

    @PutMapping( "/update/")
    public void update(@RequestBody PostDTO postDTO) {
        postService.update(postDTO);
    }

    @PutMapping( "/updateWithPhoto/")
    public void updateWithPhoto(@RequestBody PostDTO postDTO) throws IOException {
        postService.updateWithPhoto(postDTO);
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
    public List<PostListDTO> getFeaturedPosts(@RequestParam("count") Integer n) {
        LocalDateTime date = LocalDateTime.from(LocalDateTime.now().minusMonths(6));
        return postService.getFeaturedPosts(n, date);
    }

    @PostMapping("/uploadPhoto/")
    public void upload(@RequestPart MultipartFile photo, @RequestParam String newPath) throws IOException {
        fileService.save(photo, newPath);
    }


}
