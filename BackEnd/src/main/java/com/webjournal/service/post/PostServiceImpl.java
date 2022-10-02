package com.webjournal.service.post;

import com.webjournal.dto.PostDTO;
import com.webjournal.entity.Post;
import com.webjournal.exception.DatabaseFetchException;
import com.webjournal.mappers.PostMapper;
import com.webjournal.repository.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements IPostService{

    private final PostRepository repository;
    private final PostMapper mapper;

    public PostServiceImpl(PostRepository repository, PostMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Integer create(PostDTO dto) {
        Post createdPost = mapper.toEntity(new Post(), dto);
        return repository.save(createdPost).getId();
    }

    @Override
    public void delete(Integer id) throws IOException {
        repository.deleteById(id);
    }

    @Override
    public void update(PostDTO dto) throws IOException {
        Post postToUpdate = repository.findById(dto.getId()).orElseThrow(() -> new DatabaseFetchException(dto.getId(), Post.class.getSimpleName()));
        Post updatedProduct = mapper.toEntity(postToUpdate, dto);
        repository.save(updatedProduct);
    }

    @Override
    public PostDTO get(Integer id) {
        return repository.findById(id).map(mapper::toPostDto).orElseThrow(() -> new DatabaseFetchException(id, Post.class.getSimpleName()));
    }

    @Override
    public List<PostDTO> getAll() {
        return repository.findAll().stream().map(mapper::toPostDto).toList();
    }

    @Override
    public List<PostDTO> getInterestingPosts(int quantity, LocalDateTime date) {
        Pageable page = PageRequest.of(0, quantity);
        return repository.findInterestingPosts(page, date).stream().map(mapper::toPostDto).toList();
    }
}
