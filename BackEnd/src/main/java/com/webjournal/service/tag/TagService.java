package com.webjournal.service.tag;

import com.webjournal.dto.TagDTO;
import com.webjournal.entity.Tag;

import java.time.LocalDateTime;
import java.util.List;

public interface TagService {
    List<TagDTO> getActualTags(int quantity, LocalDateTime date);
    public Integer create(TagDTO dto);
    public TagDTO get(Integer id);
    List<Tag> getTags(String name);
    public List<TagDTO> getAll();
}
