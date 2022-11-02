package com.webjournal.mappers;

import com.webjournal.dto.user.AuthorDTO;
import com.webjournal.dto.user.UserDTO;
import com.webjournal.entity.User;
import com.webjournal.repository.RoleRepository;
import com.webjournal.security.payload.request.RegistrationRequest;
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
    private final RoleRepository repository;
    private final EntityManager entityManager;

    public UserMapper(RoleRepository repository, EntityManager entityManager) {
        this.repository = repository;
        this.entityManager = entityManager;
    }

    public AuthorDTO toAuthorDto(User entity) {
        AuthorDTO dto = new AuthorDTO();

        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setFollowers(entity.getCountFollowers());
        dto.setPosts(((BigInteger) entityManager.createNativeQuery("SELECT count(*) from post where author_id = ?1")
                .setParameter(1,entity.getId()).getSingleResult()).longValue());
        return dto;
    }

    public UserDTO toUserDto(User entity) {
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setEmail(entity.getEmail());
        dto.setBio(entity.getBio());
        dto.setRole(entity.getRole().getId());

        return dto;
    }
}
