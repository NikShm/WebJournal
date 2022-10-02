package com.webjournal.service.user;

import com.webjournal.dto.PostDTO;
import com.webjournal.dto.user.AuthorDTO;
import com.webjournal.dto.user.UserDTO;
import com.webjournal.entity.Post;
import com.webjournal.exception.DatabaseFetchException;
import com.webjournal.mappers.UserMapper;
import com.webjournal.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public UserServiceImpl(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    /*
    public UserDTO get(Integer id) {
        return repository.findById(id).map(mapper::).orElseThrow(() -> new DatabaseFetchException(id, Post.class.getSimpleName()));
    }
*//*
    public List<PostDTO> getAll() {
        return repository.findAll().stream().map(mapper::toPostDto).toList();
    }
    */
    public List<AuthorDTO> getInterestingAuthors(int quantity) {
        Pageable page = PageRequest.of(0, quantity);
        return repository.findInterestingAuthors(page).stream().map(mapper::toAuthorDto).toList();
    }
}
