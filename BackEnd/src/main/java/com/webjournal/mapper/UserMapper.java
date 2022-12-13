package com.webjournal.mapper;

import com.webjournal.dto.user.AuthorDTO;
import com.webjournal.dto.user.FullUserDTO;
import com.webjournal.dto.user.UserDTO;
import com.webjournal.dto.user.UserUpdateRequest;
import com.webjournal.entity.User;
import com.webjournal.service.auth.AuthServiceImpl;
import com.webjournal.service.follow.FollowServiceImpl;
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
    private final FollowServiceImpl followService;
    private final AuthServiceImpl authService;

    public UserMapper(FollowServiceImpl followService, AuthServiceImpl authService) {
        this.followService = followService;
        this.authService = authService;
    }

    public AuthorDTO toAuthorDto(User entity) {
        AuthorDTO dto = new AuthorDTO();

        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setFollowers(followService.getNumberOfUserFollowers(entity.getId()));
        dto.setBio(entity.getBio());

        return dto;
    }

    public UserDTO toUserDto(User entity) {
        UserDTO dto = new UserDTO();

        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setBio(entity.getBio());
        dto.setFollowers(followService.getNumberOfUserFollowers(entity.getId()));
        dto.setFollowing(followService.getNumberOfUserFollowings(entity.getId()));
        Object currentUser = authService.getCurrentUser();
        if (currentUser.toString().equals("anonymousUser")) {
            dto.setIsFollowing(false);
        }
        else dto.setIsFollowing(followService.isFollowing(entity.getId(), ((User) currentUser).getId()));

        return dto;
    }

    public FullUserDTO toFullUserDto(User entity) {
        FullUserDTO dto = new FullUserDTO();

        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setBio(entity.getBio());
        dto.setFollowers(followService.getNumberOfUserFollowers(entity.getId()));
        dto.setFollowing(followService.getNumberOfUserFollowings(entity.getId()));
        dto.setEmail(entity.getEmail());
        dto.setRole(entity.getRole().getRole());
        Object currentUser = authService.getCurrentUser();
        if (currentUser.toString().equals("anonymousUser")) {
            dto.setIsFollowing(false);
        }
        else dto.setIsFollowing(followService.isFollowing(entity.getId(), ((User) currentUser).getId()));

        return dto;
    }

    public User toUserEntity(User entity, UserUpdateRequest request) {
        entity.setId(request.getId());
        entity.setUsername(request.getUsername());
        entity.setBio(request.getBio());

        return entity;
    }
}
