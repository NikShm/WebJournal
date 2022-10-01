package com.webjournal.controller.user;

import com.webjournal.dto.user.AuthorDTO;
import com.webjournal.service.user.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private final UserServiceImpl service;

    public UserRestController(UserServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/top")
    public List<AuthorDTO> showInterestingAuthors(@RequestParam("count") int n) {
        return service.getInterestingAuthors(n);
    }
}
