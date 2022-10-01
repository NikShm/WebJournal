package com.webjournal.service.user;

import com.webjournal.dto.user.AuthorDTO;
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

    public List<AuthorDTO> getInterestingAuthors(int quantity) {
        Pageable page = PageRequest.of(0, quantity);
        return repository.findInterestingAuthors(page).stream().map(mapper::toAuthorDto).toList();
    }
}
