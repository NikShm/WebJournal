package com.webjournal.controller.user;

import com.webjournal.dto.user.AuthorDTO;
import com.webjournal.dto.user.UserDTO;
import com.webjournal.service.user.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users", produces = "application/json")
public class UserRestController {
    private final UserServiceImpl service;

    public UserRestController(UserServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/create/")
    public Integer create(@RequestBody UserDTO userDTO) {
        return service.create(userDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Integer id) throws IOException {
        service.delete(id);
    }

    @PutMapping( "/update/")
    public void update(@RequestBody UserDTO userDTO) throws IOException {
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
    public List<AuthorDTO> getInterestingAuthors(@RequestParam("count") int n) {
        return service.getInterestingAuthors(n);
    }
}
