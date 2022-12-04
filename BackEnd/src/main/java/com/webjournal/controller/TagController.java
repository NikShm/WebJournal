package com.webjournal.controller;

import com.webjournal.dto.CommentDTO;
import com.webjournal.dto.PostDTO;
import com.webjournal.dto.TagDTO;
import com.webjournal.entity.Tag;
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

    @GetMapping("/{id}")
    public TagDTO showOne(@PathVariable Integer id) {
        return service.get(id);
    }

    @PostMapping("/create/")
    public Integer create(@RequestBody TagDTO tagDTO) {
        return service.create(tagDTO);
    }

    @GetMapping("/actual")
    public List<TagDTO> getActualTags(@RequestParam("count") int n) {
        LocalDateTime date = LocalDateTime.from(LocalDateTime.now().minusMonths(6));
        return service.getActualTags(n, date);
    }

    @GetMapping("/")
    public List<TagDTO> showAll() {
        return service.getAll();
    }

    @GetMapping("/tag={name}")
    public List<Tag> getTags(@PathVariable String  name) {
        return service.getTags(name);
    }
}
