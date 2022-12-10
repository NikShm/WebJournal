package com.webjournal.controller;

import com.webjournal.dto.PageDTO;
import com.webjournal.dto.post.PostPreviewDTO;
import com.webjournal.dto.search.AuthorSearch;
import com.webjournal.dto.FollowDTO;
import com.webjournal.dto.search.AuthorsPostsSearch;
import com.webjournal.dto.search.SearchDTO;
import com.webjournal.dto.user.*;
import com.webjournal.entity.User;
import com.webjournal.enums.RoleType;
import com.webjournal.exception.ForbiddenException;
import com.webjournal.service.fileStorage.FilesStorageServiceImpl;
import com.webjournal.service.post.PostServiceImpl;
import com.webjournal.service.user.UserServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public UserController(UserServiceImpl service, PostServiceImpl postService, FilesStorageServiceImpl fileService) {
        this.service = service;
        this.postService = postService;
        this.fileService = fileService;
    }

    private void checkCurrentId(Integer id) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!principal.getId().equals(id)) {
            throw new ForbiddenException("Access is denied. You don't have permission to access this resource");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Integer id) {
        service.delete(id);
    }

    @PutMapping("/")
    public void update(@Valid @RequestBody UserUpdateRequest request) {
        checkCurrentId(request.getId());
        service.update(request);
    }

    @PutMapping("/role")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateRole(@Valid @RequestBody RoleUpdateRequest request) {
        service.changeRole(request);
    }

    @PostMapping("/upload-photo")
    public void uploadPhoto(@RequestPart MultipartFile photo, @RequestParam String id) throws IOException {
        checkCurrentId(Integer.valueOf(id));
        fileService.saveOrReplace(photo, "UsersIcon/user_" + id + ".jpg");
    }

    @GetMapping("/delete-photo/{id}")
    public void deletePhoto(@PathVariable String id) throws IOException {
        checkCurrentId(Integer.valueOf(id));
        fileService.delete("UsersIcon/user_" + id + ".jpg");
    }

    @GetMapping("/public/{id}")
    public UserDTO getPublicUserById(@PathVariable Integer id) {
        return service.getPublicUserById(id);
    }

    @GetMapping("/full/{id}")
    public FullUserDTO getFullUserById(@PathVariable Integer id) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        RoleType role = principal.getRole().getRole();
        if ((role == RoleType.AUTHOR || role == RoleType.MODERATOR) && !principal.getId().equals(id)) {
            throw new ForbiddenException("Access is denied. You don't have permission to access this resource");
        }
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
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getRole().getRole() == RoleType.AUTHOR && !principal.getId().equals(search.getSearchPattern().getAuthorId())) {
            throw new ForbiddenException("Access is denied. You don't have permission to access this resource");
        }
        return postService.getAuthorsPosts(search);
    }
}
