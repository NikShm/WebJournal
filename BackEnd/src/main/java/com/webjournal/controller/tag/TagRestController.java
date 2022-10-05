package com.webjournal.controller.tag;

import com.webjournal.dto.TagDTO;
import com.webjournal.service.tag.TagServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/tags", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagRestController {
    private final TagServiceImpl service;

    public TagRestController(TagServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/actual")
    public List<TagDTO> getActualTags(@RequestParam("count") int n) {
        LocalDateTime date = LocalDateTime.from(LocalDateTime.now().minusMonths(1));
        return service.getActualTags(n, date);
    }
}
