package com.webjournal.mapper;

import com.webjournal.dto.*;
import com.webjournal.entity.Post;
import com.webjournal.entity.User;
import com.webjournal.mail.service.mailtoken.MailTokenServiceImpl;
import com.webjournal.repository.UserRepository;
import com.webjournal.service.refreshtoken.RefreshTokenServiceImpl;
import com.webjournal.service.user.UserServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class PostMapper
 * @since 9/20/2022 - 22.19
 **/
@Component
public class PostMapper {
    private final UserMapper userMapper;
    private final CommentMapper commentMapper;
    private final TagMapper tagMapper;
    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    public PostMapper(UserMapper userMapper, CommentMapper commentMapper, TagMapper tagMapper, UserRepository userRepository, UserServiceImpl userService) {
        this.userMapper = userMapper;
        this.commentMapper = commentMapper;
        this.tagMapper = tagMapper;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public PostDTO toPostDto(Post entity) {
        PostDTO dto = new PostDTO();

        dto.setId(entity.getId());
        dto.setAuthor(userMapper.toAuthorDto(entity.getAuthor()));
        dto.setTitle(entity.getTitle());
        dto.setForeword(entity.getForeword());
        dto.setContent(entity.getContent());
        dto.setLikes(entity.getLikes());
        List<TagDTO> tags = entity.getTags().stream().map(tagMapper::toDto).toList();
        dto.setTags(tags);
        dto.setApproved(entity.getApproved());
        dto.setPublishedAt(entity.getPublishedAt());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public PostListDTO toPostListDto(Post entity) {
        PostListDTO listDTO = new PostListDTO();
        listDTO.setId(entity.getId());
        listDTO.setTitle(entity.getTitle());
        listDTO.setForeword(entity.getForeword());
        listDTO.setPublishedAt(entity.getPublishedAt());

        return listDTO;
    }

    public Post toEntity(Post entity, PostDTO dto) {
        entity.setId(dto.getId());
        entity.setAuthor(userRepository.getReferenceById(dto.getAuthor().getId()));
        entity.setTitle(dto.getTitle());
        entity.setForeword(dto.getForeword());
        entity.setContent(dto.getContent());
        entity.setLikes(dto.getLikes());
        entity.setApproved(dto.isApproved());
        entity.setPublishedAt(dto.getPublishedAt());

        return entity;
    }

    public Post createToEntity(Post entity, PostFormDTO dto) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        entity.setAuthor(principal);
        entity.setTitle(dto.getTitle());
        entity.setForeword(dto.getForeword());
        entity.setContent(dto.getContent());
        entity.setLikes(0);
        entity.setApproved(false);
        entity.setPublishedAt(LocalDateTime.now());

        return entity;
    }
}
