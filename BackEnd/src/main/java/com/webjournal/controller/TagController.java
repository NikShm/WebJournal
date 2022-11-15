package com.webjournal.controller;

import com.webjournal.dto.TagDTO;
import com.webjournal.service.tag.TagServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping(value = "/api/tags", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {
    private final TagServiceImpl service;

    public TagController(TagServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/actual")
    public List<TagDTO> getActualTags(@RequestParam("count") int n) {
        LocalDateTime date = LocalDateTime.from(LocalDateTime.now().minusMonths(6));
        return service.getActualTags(n, date);
    }
}
