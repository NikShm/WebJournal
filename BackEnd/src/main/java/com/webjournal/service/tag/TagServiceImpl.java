package com.webjournal.service.tag;

import com.webjournal.dto.TagDTO;
import com.webjournal.dto.search.AuthorSearch;
import com.webjournal.entity.Tag;
import com.webjournal.entity.User;
import com.webjournal.mapper.TagMapper;
import com.webjournal.repository.TagRepository;
import com.webjournal.utils.QueryHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository repository;
    private final TagMapper mapper;

    public TagServiceImpl(TagRepository repository, TagMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<TagDTO> getActualTags(int quantity, LocalDateTime date) {
        Pageable page = PageRequest.of(0, quantity);
        return repository.findActualTags(page, date).stream().map(mapper::toDto).toList();
    }

    @Override
    public List<Tag> getTags(String name) {
        Pageable page = PageRequest.of(0, 5);
        Page<Tag> all = repository.findAll((root, query, criteriaBuilder) -> getPredicate(name, criteriaBuilder, root), page);
        return all.getContent();
    }

    private Predicate getPredicate(String tagName, CriteriaBuilder criteriaBuilder, Root<Tag> user) {
        List<Predicate> predicates = new ArrayList<>();
        if (tagName != null) {
            predicates.add(QueryHelper.ilike(user.get("name"), criteriaBuilder, tagName));
        }
        return predicates.size() == 1 ? predicates.get(0) : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
