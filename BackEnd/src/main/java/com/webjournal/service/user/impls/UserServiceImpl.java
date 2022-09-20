package com.webjournal.service.user.impls;

import com.webjournal.dto.AuthorDTO;
import com.webjournal.entity.User;
import com.webjournal.mappers.UserMapper;
import com.webjournal.repository.UserRepository;
import com.webjournal.service.user.interfaces.IUserService;
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

    public List<AuthorDTO> getInterestingBloggers(int quantity) {
        Pageable page = PageRequest.of(0, quantity);
        return repository.findTopBloggers(page).stream().map(mapper::toAuthorDto).toList();
    }
}
