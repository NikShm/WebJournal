package com.webjournal.service.comment;

import com.webjournal.dto.CommentDTO;
import com.webjournal.dto.PageDTO;
import com.webjournal.dto.search.CommentSearch;
import com.webjournal.dto.search.SearchDTO;
import com.webjournal.entity.Comment;
import com.webjournal.entity.User;
import com.webjournal.enums.SortDirection;
import com.webjournal.exception.DatabaseFetchException;
import com.webjournal.mapper.CommentMapper;
import com.webjournal.repository.CommentRepository;
import com.webjournal.service.post.PostServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements ICommentService{
    private final CommentRepository repository;
    private final CommentMapper mapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);

    public CommentServiceImpl(CommentRepository repository, CommentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Integer create(CommentDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authorizeUser = (User)authentication.getPrincipal();
        dto.getAuthor().setId(authorizeUser.getId());
        Comment createdComment = mapper.toEntity(new Comment(), dto);
        return repository.save(createdComment).getId();
    }

    @Override
    public void delete(Integer id) throws IOException {
        Comment comment = repository.findById(id).orElseThrow(() -> new DatabaseFetchException("Could not find Post entity with id " + id));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authorizeUser = (User)authentication.getPrincipal();
        if (authorizeUser.getId().equals(comment.getAuthor().getId())) {
            repository.deleteById(id);
        }
    }

    @Override
    public void update(CommentDTO dto) throws IOException {
        Comment commentToUpdate = repository.findById(dto.getId()).orElseThrow(() -> new DatabaseFetchException("Could not find Post entity with id " + dto.getId()));
        Comment updatedComment = mapper.toEntity(commentToUpdate, dto);
        repository.save(updatedComment);
    }

    @Override
    public PageDTO<CommentDTO> getPage(SearchDTO<CommentSearch>  search) {
        LOGGER.info("Enter to getPage method, in class CommentServiceImpl. Search parameters {}", search);
        Sort sort = Sort.by(search.getSortField());
        if (search.getSortDirection() == SortDirection.DESC) {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(search.getPage(), search.getPageSize(), sort);
        Page<Comment> all = repository.findAll((root, query, criteriaBuilder) -> getPredicate(search.getSearchPattern(), criteriaBuilder, root),pageable);
        PageDTO<CommentDTO> dto = new PageDTO<>();
        dto.setContent(all.stream().map(mapper::toDto).collect(Collectors.toList()));
        dto.setTotalItem(all.getTotalElements());
        return dto;
    }

    private Predicate getPredicate(CommentSearch search, CriteriaBuilder criteriaBuilder, Root<Comment> comment) {
        List<Predicate> predicates = new ArrayList<>();
        String value = search.getPostId();
        if (value != null) {
            predicates.add(criteriaBuilder.equal(comment.get("postId"),value));
        }
        return predicates.size() == 1 ? predicates.get(0) : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
