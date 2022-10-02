package com.webjournal.mappers;

import com.webjournal.dto.CommentDTO;
import com.webjournal.entity.Comment;
import com.webjournal.repository.UserRepository;
import org.springframework.stereotype.Component;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class CommentMapper
 * @since 9/20/2022 - 22.25
 **/
@Component
public class CommentMapper {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public CommentMapper(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public CommentDTO toDto(Comment entity) {
        CommentDTO dto = new CommentDTO();

        dto.setId(entity.getId());
        dto.setAuthor(userMapper.toAuthorDto(entity.getAuthor()));
        dto.setText(entity.getText());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public Comment toEntity(Comment entity, CommentDTO dto) {
        entity.setId(dto.getId());
        entity.setAuthor(userRepository.getReferenceById(dto.getAuthor().getId()));
        entity.setText(dto.getText());

        return entity;
    }
}
