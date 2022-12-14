package com.webjournal.mapper;

import com.webjournal.dto.*;
import com.webjournal.dto.post.PostFormDTO;
import com.webjournal.dto.post.PostListDTO;
import com.webjournal.dto.post.PostPreviewDTO;
import com.webjournal.entity.Post;
import com.webjournal.entity.Tag;
import com.webjournal.entity.User;
import com.webjournal.repository.UserRepository;
import com.webjournal.service.user.UserServiceImpl;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    private final EntityManager entityManager;

    public PostMapper(UserMapper userMapper, CommentMapper commentMapper, TagMapper tagMapper, UserRepository userRepository, UserServiceImpl userService, EntityManager entityManager) {
        this.userMapper = userMapper;
        this.commentMapper = commentMapper;
        this.tagMapper = tagMapper;
        this.userRepository = userRepository;
        this.userService = userService;
        this.entityManager = entityManager;
    }

    public PostDTO toPostDto(Post entity) {
        PostDTO dto = new PostDTO();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authorizeUser = null;

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
        if (authentication.getPrincipal() != "anonymousUser") {
            authorizeUser = (User) authentication.getPrincipal();
            dto.setLike((Boolean) entityManager.createNativeQuery("select exists(select 1 from \"like\" where user_id=?1 and post_id=?2)")
                    .setParameter(1, authorizeUser.getId())
                    .setParameter(2, dto.getId())
                    .getSingleResult());
        }
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
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        entity.setAuthor(principal);
        entity.setTitle(dto.getTitle());
        entity.setForeword(dto.getForeword());
        Set<Tag> tags = dto.getTags().stream().map(tagMapper::toEntity).collect(Collectors.toSet());
        entity.setTags(tags);

        if(dto.getContent().contains("&#")){
            StringBuilder newStr = new StringBuilder(dto.getContent().replace(";", "&#"));

            String[] text = newStr.toString().split("&#");

            newStr = new StringBuilder();
            for (String s : text) {
                if(s.length() <= 4 && s.matches("[-+]?\\d+")){
                    int m = Integer.parseInt(s, 10);
                    newStr.append(Character.toString(m));
                }
                else {
                    newStr.append(s);
                }
            }

            entity.setContent(String.valueOf(newStr));
        }
        else {
            entity.setContent(dto.getContent());
        }

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
        Set<Tag> tags = dto.getTags().stream().map(tagMapper::toEntity).collect(Collectors.toSet());
        entity.setTags(tags);

        if(dto.getContent().contains("&#")){
            StringBuilder newStr = new StringBuilder(dto.getContent().replace(";", "&#"));

            String[] text = newStr.toString().split("&#");

            newStr = new StringBuilder();
            for (String s : text) {
                if(s.length() <= 4 && s.matches("[-+]?\\d+")){
                    int m = Integer.parseInt(s, 10);
                    newStr.append(Character.toString(m));
                }
                else {
                    newStr.append(s);
                }
            }

            entity.setContent(String.valueOf(newStr));
        }
        else {
            entity.setContent(dto.getContent());
        }
        entity.setLikes(0);
        entity.setApproved(false);
        entity.setPublishedAt(LocalDateTime.now());
        return entity;
    }

    public PostPreviewDTO toPostPreviewDTO(Post entity) {
        PostPreviewDTO previewDTO = new PostPreviewDTO();

        previewDTO.setId(entity.getId());
        previewDTO.setTitle(entity.getTitle());
        previewDTO.setForeword(entity.getForeword());
        Tag tag = entity.getTags().stream().findFirst().orElse(null);
        String label = tag == null ? "" : tag.getName();
        previewDTO.setTag(label);

        return previewDTO;
    }
}
