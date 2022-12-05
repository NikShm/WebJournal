package com.webjournal.controller;

import com.webjournal.dto.PageDTO;
import com.webjournal.dto.post.PostPreviewDTO;
import com.webjournal.dto.search.AuthorSearch;
import com.webjournal.dto.FollowDTO;
import com.webjournal.dto.search.AuthorsPostsSearch;
import com.webjournal.dto.search.SearchDTO;
import com.webjournal.dto.user.AuthorDTO;
import com.webjournal.dto.user.UserDTO;
import com.webjournal.entity.User;
import com.webjournal.exception.ForbiddenException;
import com.webjournal.service.post.PostServiceImpl;
import com.webjournal.service.user.UserServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserServiceImpl service;
    private final PostServiceImpl postService;

    public UserController(UserServiceImpl service, PostServiceImpl postService) {
        this.service = service;
        this.postService = postService;
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Integer id) {
        service.delete(id);
    }

    /*@PutMapping( "/update/")
    public void update(@RequestBody UserDTO userDTO) {
        service.update(userDTO);
    }*/

    @GetMapping("/{username}")
    public UserDTO getByUsername(@PathVariable String username) {
        return service.getByUsername(username);
    }

    @GetMapping("/author/{id}")
    public AuthorDTO getById(@PathVariable Integer id) {
        return service.getAuthorById(id);
    }

    @RequestMapping("/")
    List<UserDTO> showAll(){
        return service.getAll();
    }

    @GetMapping("/top")
    public List<AuthorDTO> getInterestingAuthors(@RequestParam("count") Integer n) {
        return service.getInterestingAuthors(n);
    }

    @PostMapping("/search")
    public PageDTO<AuthorDTO> getPage(@RequestBody SearchDTO<AuthorSearch> search) {
        return service.getAuthorPage(search);
    }

    @PostMapping("/unsubscribe")
    public void unsubscribe(@RequestBody FollowDTO followDTO){
        service.unsubscribe(followDTO);
    }

    @PostMapping("/subscribe")
    public void subscribe(@RequestBody FollowDTO followDTO){
        service.subscribe(followDTO);
    }

    @PostMapping("/{id}/posts-approved")
    public PageDTO<PostPreviewDTO> getApprovedAuthorsPosts(@RequestBody SearchDTO<String> search) {
        return postService.getApprovedAuthorsPosts(search);
    }

    @PostMapping("/{id}/posts-filtered")
    public PageDTO<PostPreviewDTO> getAuthorsPosts(@RequestBody SearchDTO<AuthorsPostsSearch> search) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.toString().equals("anonymousUser") || !((User) principal).getId().equals(search.getSearchPattern().getAuthorId())) {
            throw new ForbiddenException("Access is denied. You don't have permission to access this resource");
        }
        return postService.getAuthorsPosts(search);
    }
}
