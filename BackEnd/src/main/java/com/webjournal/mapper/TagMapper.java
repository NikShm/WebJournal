package com.webjournal.mapper;

import com.webjournal.dto.TagDTO;
import com.webjournal.entity.Tag;
import com.webjournal.repository.TagRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class TagMapper
 * @since 9/20/2022 - 22.32
 **/
@Component
public class TagMapper {

    final TagRepository repository;

    public TagMapper(TagRepository repository) {
        this.repository = repository;
    }

    public TagDTO toDto(Tag entity) {
        TagDTO dto = new TagDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }


    public Tag toEntity(TagDTO dto) {
        Tag entity = new Tag();

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }

    public Tag toCreateEntity(TagDTO dto) {
        Tag entity = new Tag();
        entity.setName(dto.getName());
        return entity;
    }
}
