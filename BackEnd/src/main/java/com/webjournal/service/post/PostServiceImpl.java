package com.webjournal.service.post;

import com.webjournal.dto.PostDTO;
import com.webjournal.entity.Post;
import com.webjournal.mappers.PostMapper;
import com.webjournal.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl {

    private final PostRepository repository;
    private final PostMapper mapper;

    public PostServiceImpl(PostRepository repository, PostMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<PostDTO> getInterestingPosts(int quantity, LocalDateTime date) {
        Pageable page = PageRequest.of(0, quantity);
        return repository.findInterestingPosts(page, date).stream().map(mapper::toPostDto).toList();
    }

    public List<PostDTO> getAll() {
        return repository.findAll().stream().map(mapper::toPostDto).toList();
    }
}
