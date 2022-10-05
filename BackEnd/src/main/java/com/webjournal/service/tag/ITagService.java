package com.webjournal.service.tag;

import com.webjournal.dto.TagDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ITagService {
    List<TagDTO> getActualTags(int quantity, LocalDateTime date);
}
