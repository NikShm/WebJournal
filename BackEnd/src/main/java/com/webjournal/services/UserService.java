package com.webjournal.services;/*
@author Микола
@project High_school_library
@class UserService
@version 1.0.0
@since 17.08.2022 - 22.30
*/

import com.webjournal.dto.PageDTO;
import com.webjournal.dto.SearchDTO;
import com.webjournal.dto.AuthorDTO;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<AuthorDTO> getAll();

    PageDTO<AuthorDTO> getPage(SearchDTO search);

    Optional<AuthorDTO> getOneUser(Integer id);

    void update(AuthorDTO dto) throws IOException;

    void delete(Integer id) throws IOException;

    Integer create(AuthorDTO dto);
}
