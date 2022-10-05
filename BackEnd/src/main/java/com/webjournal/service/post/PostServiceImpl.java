package com.webjournal.service.post;

import com.webjournal.dto.PageDTO;
import com.webjournal.dto.PostDTO;
import com.webjournal.dto.SearchDTO;
import com.webjournal.entity.Post;
import com.webjournal.exception.DatabaseFetchException;
import com.webjournal.mappers.PostMapper;
import com.webjournal.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements IPostService{
    private final PostRepository repository;
    private final PostMapper postMapper;
    private final EntityManager entityManager;

    public PostServiceImpl(PostRepository repository, PostMapper postMapper, EntityManager entityManager) {
        this.repository = repository;
        this.postMapper = postMapper;
        this.entityManager = entityManager;
    }

    @Override
    public Integer create(PostDTO dto) {
        Post createdPost = postMapper.toEntity(new Post(), dto);
        return repository.save(createdPost).getId();
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void update(PostDTO dto) {
        Post postToUpdate = repository.findById(dto.getId()).orElseThrow(() -> new DatabaseFetchException(dto.getId(), Post.class.getSimpleName()));
        Post updatedProduct = postMapper.toEntity(postToUpdate, dto);
        repository.save(updatedProduct);
    }

    @Override
    public PostDTO get(Integer id) {
        return repository.findById(id).map(postMapper::toPostDto).orElseThrow(() -> new DatabaseFetchException(id, Post.class.getSimpleName()));
    }

    @Override
    public List<PostDTO> getAll() {
        return repository.findAll().stream().map(postMapper::toPostDto).toList();
    }

    @Override
    public List<PostDTO> getFeaturedPosts(int quantity, LocalDateTime date) {
        Pageable page = PageRequest.of(0, quantity);
        return repository.findInterestingPosts(page, date).stream().map(postMapper::toPostDto).toList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageDTO<PostDTO> getPage(SearchDTO searchDTO) {
        List<PostDTO> postDTOS = new ArrayList<>();
        for (Object entity : entityManager.createNativeQuery(getQuery(searchDTO), Post.class).getResultList()) {
            postDTOS.add(postMapper.toPostDto((Post) entity));
        }
        Page<PostDTO> page = new PageImpl<>(postDTOS);
        PageDTO<PostDTO> pageDTO = new PageDTO<>();
        pageDTO.setContent(page.getContent());
        pageDTO.setTotalItem(page.getTotalElements());
        return pageDTO;
    }

    /**
     * Для реалізації, пагінації, в даному випадку я використав Native Query, через те що треба було реалізувати,
     * fullTextSearch. Hibernate Search я не зміг використати через те, що він використовує свій тип предикати, який
     * не піддходе для реалізаціх пагінвції через репозиторій. Також у criteriaBuilder немає відповідної функції, яка б
     * повернула потрібну предикату. Чому не я зміг реалізувати свою предикату дивіться нижче.
     *
     * @see com.webjournal.utils.FullTextSearchPred
     */
    private String getQuery(SearchDTO search) {
        StringBuilder query = new StringBuilder("SELECT * FROM post");
        if (search.getSearch() != null) {
            query.append(" WHERE post.ts_content @@ phraseto_tsquery('english', '").append(search.getSearch()).append("')");
            query.append(" or post.ts_title @@ phraseto_tsquery('english', '").append(search.getSearch()).append("')");
        }
        if (search.getSortDirection() != null && search.getSortField() != null) {
            query.append(" ORDER BY ").append(search.getSortField()).append(" ").append(search.getSortDirection());
        }
        if (search.getPage() != null && search.getPageSize() != null) {
            query.append(" limit ").append(search.getPageSize()).append(" offset ").append(search.getPage());
        }
        return query.toString();
    }
}
