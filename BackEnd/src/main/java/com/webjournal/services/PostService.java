package com.webjournal.services;/*
@author Микола
@project High_school_library
@class PostService
@version 1.0.0
@since 05.09.2022 - 22.05
*/

import com.webjournal.dto.PageDTO;
import com.webjournal.dto.SearchDTO;
import com.webjournal.entity.Post;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PostService {
    Page<Post> getPage(SearchDTO searchDTO);
    /*PageDTO<AuthorDTO> getPage(SearchDTO search);

    Optional<AuthorDTO> getOneUser(Integer id);*/
}
