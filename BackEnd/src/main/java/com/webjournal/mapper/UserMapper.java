package com.webjournal.mapper;

import com.webjournal.dto.user.AuthorDTO;
import com.webjournal.dto.user.FullUserDTO;
import com.webjournal.dto.user.UserDTO;
import com.webjournal.dto.user.UserUpdateRequest;
import com.webjournal.entity.User;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.math.BigInteger;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class UserMapper
 * @since 9/20/2022 - 18.31
 **/
@Component
public class UserMapper {
    private final EntityManager entityManager;

    public UserMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public AuthorDTO toAuthorDto(User entity) {
        AuthorDTO dto = new AuthorDTO();

        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setFollowers(entity.getCountFollowers());
        dto.setBio(entity.getBio());

        return dto;
    }

    public UserDTO toUserDto(User entity) {
        UserDTO dto = new UserDTO();

        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setBio(entity.getBio());
        dto.setFollowers(entity.getCountFollowers());
        dto.setFollowing((int) ((BigInteger) entityManager.createNativeQuery("SELECT COUNT(*) from follow where following_user_id = ?1")
                .setParameter(1, entity.getId()).getSingleResult()).longValue());

        return dto;
    }

    public FullUserDTO toFullUserDto(User entity) {
        FullUserDTO dto = new FullUserDTO();

        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setBio(entity.getBio());
        dto.setFollowers(entity.getCountFollowers());
        dto.setFollowing((int) ((BigInteger) entityManager.createNativeQuery("SELECT COUNT(*) from follow where following_user_id = ?1")
                .setParameter(1, entity.getId()).getSingleResult()).longValue());
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole().getRole());

        return dto;
    }

    public User toUserEntity(User entity, UserUpdateRequest request) {
        entity.setId(request.getId());
        entity.setUsername(request.getUsername());
        entity.setBio(request.getBio());

        return entity;
    }


}
