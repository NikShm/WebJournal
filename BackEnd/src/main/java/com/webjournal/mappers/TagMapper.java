package com.webjournal.mappers;

import com.webjournal.dto.TagDTO;
import com.webjournal.entity.Tag;
import org.springframework.stereotype.Component;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class TagMapper
 * @since 9/20/2022 - 22.32
 **/
@Component
public class TagMapper {
    public TagDTO toDto(Tag entity) {
        TagDTO dto = new TagDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }
}
