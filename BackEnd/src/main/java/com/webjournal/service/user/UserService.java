package com.webjournal.service.user;

import com.webjournal.dto.FollowDTO;
import com.webjournal.dto.PageDTO;
import com.webjournal.dto.search.AuthorSearch;
import com.webjournal.dto.search.SearchDTO;
import com.webjournal.dto.user.AuthorDTO;
import com.webjournal.dto.user.UserDTO;
import com.webjournal.dto.user.UserUpdateRequest;
import com.webjournal.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    AuthorDTO getAuthorById(Integer id);
    User getByUsernameButGetUser(String username);
    void delete(Integer id);
    void update(UserUpdateRequest request);
    UserDTO getById(Integer id);
    List<UserDTO> getAll();
    List<AuthorDTO> getInterestingAuthors(int quantity);
    @Transactional
    void subscribe(FollowDTO followDTO);
    @Transactional
    void unsubscribe(FollowDTO followDTO);
    PageDTO<AuthorDTO> getAuthorPage(SearchDTO<AuthorSearch> search);
    UserDTO getUserById(Integer id);
}
