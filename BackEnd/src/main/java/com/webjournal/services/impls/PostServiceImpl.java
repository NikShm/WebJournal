package com.webjournal.services.impls;


import com.webjournal.dto.AuthorDTO;
import com.webjournal.dto.PageDTO;
import com.webjournal.dto.SearchDTO;
import com.webjournal.mappers.PostMapper;
import com.webjournal.repositories.PostRepository;
import com.webjournal.services.PostService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
@author Микола
@project High_school_library
@class PostServiceImpl
@version 1.0.0
@since 05.09.2022 - 22.05
*/
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository repository;
    private final PostMapper mapper;

    public PostServiceImpl(PostRepository repository, PostMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PageDTO<AuthorDTO> getPage(SearchDTO search) {
        return null;
    }

    @Override
    public Optional<AuthorDTO> getOneUser(Integer id) {
        return Optional.empty();
    }
}
