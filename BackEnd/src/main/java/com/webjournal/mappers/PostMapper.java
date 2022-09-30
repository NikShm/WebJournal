package com.webjournal.mappers;

import com.webjournal.dto.CommentDTO;
import com.webjournal.dto.PostDTO;
import com.webjournal.dto.TagDTO;
import com.webjournal.entity.Post;
import org.springframework.stereotype.Component;

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

    public PostMapper(UserMapper userMapper, CommentMapper commentMapper, TagMapper tagMapper) {
        this.userMapper = userMapper;
        this.commentMapper = commentMapper;
        this.tagMapper = tagMapper;
    }

    public PostDTO toPostDto(Post entity) {
        PostDTO dto = new PostDTO();

        dto.setId(entity.getId());
        dto.setAuthor(userMapper.toAuthorDto(entity.getAuthor()));
        dto.setTitle(entity.getTitle());
        dto.setForeword(entity.getForeword());
        dto.setContent(entity.getContent());
        dto.setLikes(0); // TODO get number of likes by query
        List<CommentDTO> comments = entity.getComments().stream().map(commentMapper::toDto).toList();
        dto.setComments(comments);
        List<TagDTO> tags = entity.getTags().stream().map(tagMapper::toDto).toList();
        dto.setTags(tags);
        dto.setApproved(entity.getApproved());
        dto.setPublishedAt(entity.getPublishedAt());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }
}
