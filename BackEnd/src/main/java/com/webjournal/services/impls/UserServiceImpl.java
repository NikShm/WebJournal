package com.webjournal.services.impls;


import com.webjournal.dto.AuthorDTO;
import com.webjournal.dto.PageDTO;
import com.webjournal.dto.SearchDTO;
import com.webjournal.entities.Author;
import com.webjournal.entities.User;
import com.webjournal.enums.SortDirection;
import com.webjournal.exceptions.DatabaseFetchException;
import com.webjournal.mappers.UserMapper;
import com.webjournal.repositories.UserRepository;
import com.webjournal.services.UserService;
import com.webjournal.utils.QueryHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
@author Микола
@project High_school_library
@class UserServiceImpl
@version 1.0.0
@since 17.08.2022 - 22.31
*/
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserServiceImpl(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<AuthorDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public PageDTO<AuthorDTO> getPage(SearchDTO search) {
        Sort sort = Sort.by(search.getSortField());
        if (search.getSortDirection() == SortDirection.DESC) {
            sort = sort.descending();
        }
        Pageable pageable = PageRequest.of(search.getPage(), search.getPageSize(), sort);
        Page<Author> all = repository.findAll((root, query, criteriaBuilder) -> getPredicate(search, criteriaBuilder, root), pageable);
        PageDTO<AuthorDTO> dto = new PageDTO<>();
        dto.setContent(all.stream().map(mapper::toDto).collect(Collectors.toList()));
        dto.setPageCount(all.getTotalPages());
        dto.setPage(all.getNumber());
        dto.setPageSize(all.getNumberOfElements());
        dto.setTotalItem(all.getTotalElements());
        return dto;
    }

    private Predicate getPredicate(SearchDTO search, CriteriaBuilder criteriaBuilder, Root<Author> product) {
        List<Predicate> predicates = new ArrayList<>();
        String value = search.getSearch();
        if (value != null) {
            predicates.add(criteriaBuilder.or(QueryHelper.ilike(product.get("surname"), criteriaBuilder, value),
                    QueryHelper.ilike(product.get("name"), criteriaBuilder, value)));
        }
        return predicates.size() == 1 ? predicates.get(0) : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    @Override
    public Optional<AuthorDTO> getOneUser(Integer id) {
        return repository.findById(id).map(mapper::toDto);
    }

    @Override
    public void update(AuthorDTO dto) throws IOException {
        repository.findById(dto.getId()).orElseThrow(() -> new DatabaseFetchException(dto.getId(), User.class.getSimpleName()));
        Author updatedAuthor = mapper.toEntity(dto);
        repository.save(updatedAuthor);
    }

    @Override
    public void delete(Integer id) throws IOException {
        repository.findById(id).orElseThrow(() -> new DatabaseFetchException(id, User.class.getSimpleName()));
        repository.deleteById(id);
    }

    @Override
    public Integer create(AuthorDTO dto) {
        Author updatedAuthor = mapper.toEntity(dto);
        return repository.save(updatedAuthor).getId();
    }
}
