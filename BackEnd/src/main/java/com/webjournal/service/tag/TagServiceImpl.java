package com.webjournal.service.tag;

import com.webjournal.dto.TagDTO;
import com.webjournal.mappers.TagMapper;
import com.webjournal.repository.TagRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TagServiceImpl implements ITagService {
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
}
