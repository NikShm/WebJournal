package com.webjournal.service.user;

import com.webjournal.dto.PageDTO;
import com.webjournal.dto.search.AuthorSearch;
import com.webjournal.dto.search.SearchDTO;
import com.webjournal.dto.user.*;
import com.webjournal.entity.User;

import java.util.List;

public interface UserService {
    FullUserDTO getFullUserById(Integer id);
    User getEntityById(Integer id);
    void delete(Integer id);
    void update(UserUpdateRequest request);
    UserDTO getPublicUserById(Integer id);
    void changeRole(RoleUpdateRequest request);
    List<UserDTO> getAll();
    List<AuthorDTO> getInterestingAuthors(int quantity);
    PageDTO<AuthorDTO> getAuthorPage(SearchDTO<AuthorSearch> search);
}
