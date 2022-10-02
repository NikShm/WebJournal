package com.webjournal.service.user;

import com.webjournal.dto.PostDTO;
import com.webjournal.dto.user.AuthorDTO;
import com.webjournal.dto.user.UserDTO;

import java.io.IOException;
import java.util.List;

public interface IUserService {
    Integer create(UserDTO dto);
    void delete(Integer id) throws IOException;
    void update(UserDTO dto) throws IOException;
    UserDTO get(Integer id);
    List<UserDTO> getAll();
    List<AuthorDTO> getInterestingAuthors(int quantity);
}
