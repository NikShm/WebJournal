package com.webjournal.service.comment;

import com.webjournal.dto.CommentDTO;
import com.webjournal.dto.PageDTO;
import com.webjournal.dto.search.SearchCommentDTO;
import com.webjournal.entity.Comment;
import com.webjournal.enums.SortDirection;
import com.webjournal.exception.DatabaseFetchException;
import com.webjournal.mappers.CommentMapper;
import com.webjournal.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements ICommentService{
    private final CommentRepository repository;
    private final CommentMapper mapper;

    public CommentServiceImpl(CommentRepository repository, CommentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Integer create(CommentDTO dto) {
        Comment createdComment = mapper.toEntity(new Comment(), dto);
        return repository.save(createdComment).getId();
    }

    @Override
    public void delete(Integer id) throws IOException {
        repository.deleteById(id);
    }

    @Override
    public void update(CommentDTO dto) throws IOException {
        Comment commentToUpdate = repository.findById(dto.getId()).orElseThrow(() -> new DatabaseFetchException(dto.getId(), Comment.class.getSimpleName()));
        Comment updatedComment = mapper.toEntity(commentToUpdate, dto);
        repository.save(updatedComment);
    }

    @Override
    public PageDTO<CommentDTO> getPage(SearchCommentDTO search) {
        Sort sort = Sort.by(search.getSortField());
        if (search.getSortDirection() == SortDirection.DESC) {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(search.getPage(), search.getPageSize(), sort);
        Page<Comment> all = repository.findAll(pageable);
        PageDTO<CommentDTO> dto = new PageDTO<>();
        dto.setContent(all.stream().map(mapper::toDto).collect(Collectors.toList()));
        dto.setTotalItem(all.getTotalElements());
        return dto;
    }
}
