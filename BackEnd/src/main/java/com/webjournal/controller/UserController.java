package com.webjournal.controller;

import com.webjournal.dto.FollowDTO;
import com.webjournal.dto.user.AuthorDTO;
import com.webjournal.dto.user.UserDTO;
import com.webjournal.service.user.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users", produces = "application/json")
public class UserController {
    private final UserServiceImpl service;

    public UserController(UserServiceImpl service) {
        this.service = service;
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Integer id) {
        service.delete(id);
    }

    @PutMapping( "/update/")
    public void update(@RequestBody UserDTO userDTO) {
        service.update(userDTO);
    }

    @GetMapping("/{id}")
    public UserDTO showOne(@PathVariable Integer id) {
        return service.get(id);
    }

    @RequestMapping("/")
    List<UserDTO> showAll(){
        return service.getAll();
    }

    @GetMapping("/top")
    public List<AuthorDTO> getInterestingAuthors(@RequestParam("count") Integer n) {
        return service.getInterestingAuthors(n);
    }

    @PostMapping("/unsubscribe")
    public void unsubscribe(@RequestBody FollowDTO followDTO){
        service.unsubscribe(followDTO);
    }

    @PostMapping("/subscribe")
    public void subscribe(@RequestBody FollowDTO followDTO){
        service.subscribe(followDTO);
    }
}
