package com.webjournal.mappers;

import com.webjournal.dto.user.AuthorDTO;
import com.webjournal.dto.user.UserDTO;
import com.webjournal.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class UserMapper
 * @since 9/20/2022 - 18.31
 **/
@Component
public class UserMapper {
    public AuthorDTO toAuthorDto(User entity) {
        AuthorDTO dto = new AuthorDTO();

        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setFollowers(entity.getFollowers().size());

        return dto;
    }

    public User toEntity(AuthorDTO dto) {
        User entity = new User();

        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setFollowers(entity.getFollowers().size());

        return entity;
    }
}
