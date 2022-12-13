package com.webjournal.controller;

import com.webjournal.dto.MessageResponse;
import com.webjournal.dto.PageDTO;
import com.webjournal.dto.post.PostPreviewDTO;
import com.webjournal.dto.search.AuthorSearch;
import com.webjournal.dto.search.AuthorsPostsSearch;
import com.webjournal.dto.search.SearchDTO;
import com.webjournal.dto.user.*;
import com.webjournal.service.fileStorage.FilesStorageServiceImpl;
import com.webjournal.service.follow.FollowServiceImpl;
import com.webjournal.service.post.PostServiceImpl;
import com.webjournal.service.user.UserServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserServiceImpl service;
    private final PostServiceImpl postService;
    private final FilesStorageServiceImpl fileService;
    private final FollowServiceImpl followService;

    public UserController(UserServiceImpl service, PostServiceImpl postService, FilesStorageServiceImpl fileService, FollowServiceImpl followService) {
        this.service = service;
        this.postService = postService;
        this.fileService = fileService;
        this.followService = followService;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') || #id == authentication.principal.id")
    public void deleteOne(@PathVariable Integer id) {
        service.delete(id);
    }

    @PutMapping("/")
    @PreAuthorize("#request.id == authentication.principal.id")
    public void update(@Valid @RequestBody UserUpdateRequest request) {
        service.update(request);
    }

    @PutMapping("/role")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateRole(@Valid @RequestBody RoleUpdateRequest request) {
        service.changeRole(request);
    }

    @PostMapping("/upload-photo")
    @PreAuthorize("#id == authentication.principal.id")
    public void uploadPhoto(@RequestPart MultipartFile photo, @RequestParam String id) throws IOException {
        fileService.saveOrReplace(photo, "UsersIcon/user_" + id + ".jpg");
    }

    @GetMapping("/delete-photo/{id}")
    @PreAuthorize("#id == authentication.principal.id")
    public void deletePhoto(@PathVariable String id) throws IOException {
        fileService.delete("UsersIcon/user_" + id + ".jpg");
    }

    @GetMapping("/public/{id}")
    public UserDTO getPublicUserById(@PathVariable Integer id) {
        return service.getPublicUserById(id);
    }

    @GetMapping("/full/{id}")
    @PreAuthorize("hasRole('ADMIN') || #id == authentication.principal.id")
    public FullUserDTO getFullUserById(@PathVariable Integer id) {
        return service.getFullUserById(id);
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

    @DeleteMapping ("/{userId}/unfollow/{userToUnfollowId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public MessageResponse unfollow(@PathVariable Integer userId, @PathVariable Integer userToUnfollowId) {
        followService.unfollow(userId, userToUnfollowId);
        return new MessageResponse("Successfully unfollowed user № " + userToUnfollowId);
    }

    @GetMapping("/{userId}/follow/{userToFollowId}")
    @PreAuthorize("#userId == authentication.principal.id")
    public MessageResponse follow(@PathVariable Integer userId, @PathVariable Integer userToFollowId) {
        followService.follow(userId, userToFollowId);
        return new MessageResponse("Successfully followed user № " + userToFollowId);
    }

    @PostMapping("/{authorId}/posts-approved")
    public PageDTO<PostPreviewDTO> getApprovedAuthorsPosts(@RequestBody SearchDTO<AuthorsPostsSearch> search, @PathVariable Integer authorId) {
        search.setSearchPattern(new AuthorsPostsSearch(true));
        return postService.getAuthorsPosts(search, authorId);
    }

    @PostMapping("/{authorId}/posts-filtered")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR') || authentication.principal.id == #authorId")
    public PageDTO<PostPreviewDTO> getAuthorsPosts(@RequestBody SearchDTO<AuthorsPostsSearch> search, @PathVariable Integer authorId) {
        return postService.getAuthorsPosts(search, authorId);
    }
}
