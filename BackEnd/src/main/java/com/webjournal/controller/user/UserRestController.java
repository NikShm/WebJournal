package com.webjournal.controller.user;

import com.webjournal.dto.AuthorDTO;
import com.webjournal.service.user.impls.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserServiceImpl service;

    public UserRestController(UserServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/top-{n}")
    public List<AuthorDTO> showTopBloggers(@PathVariable int n) {
       return service.getInterestingBloggers(n);
    }
}
