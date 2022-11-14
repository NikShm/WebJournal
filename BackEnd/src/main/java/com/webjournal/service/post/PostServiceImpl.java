package com.webjournal.service.post;

import com.webjournal.dto.*;
import com.webjournal.dto.LikeDTO;
import com.webjournal.dto.PageDTO;
import com.webjournal.dto.PostDTO;
import com.webjournal.dto.search.PostSearch;
import com.webjournal.dto.search.SearchDTO;
import com.webjournal.entity.Post;
import com.webjournal.exception.DatabaseFetchException;
import com.webjournal.mapper.PostMapper;
import com.webjournal.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PostServiceImpl implements IPostService {

    private final PostRepository repository;
    private final PostMapper postMapper;
    private final EntityManager entityManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);

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
        Post postToUpdate = repository.findById(dto.getId()).orElseThrow(() -> new DatabaseFetchException("Could not find Post entity with id " + dto.getId()));
        Post updatedProduct = postMapper.toEntity(postToUpdate, dto);
        repository.save(updatedProduct);
    }

    @Override
    public PostDTO get(Integer id) {
        return repository.findById(id).map(postMapper::toPostDto).orElseThrow(() -> new DatabaseFetchException("Could not find Post entity with id " + id));
    }

    @Override
    public List<PostDTO> getAll() {
        return repository.findAll().stream().map(postMapper::toPostDto).toList();
    }

    @Override
    public List<PostListDTO> getFeaturedPosts(Integer quantity, LocalDateTime date) {
        Pageable page = PageRequest.of(0, quantity);
        return repository.findInterestingPosts(page, date).stream().map(postMapper::toPostListDto).toList();
    }

    @Transactional
    @Override
    public void like(LikeDTO like) {
        if (like.getPostId() != null && like.getUserId() != null) {
            Integer authorId = (Integer) entityManager.createNativeQuery("SELECT author_id from post where id = ?1")
                    .setParameter(1, + like.getPostId())
                    .getSingleResult();
            Boolean isExist = (Boolean) entityManager.createNativeQuery("select exists(select 1 from \"like\" where user_id=?1 and post_id=?2)")
                    .setParameter(1, like.getUserId())
                    .setParameter(2, like.getPostId())
                    .getSingleResult();
            if (!Objects.equals(authorId, like.getUserId()) && !isExist) {
                String INSERT_LIKE = "insert into \"like\"(user_id, post_id) VALUES (?1,?2) ON CONFLICT DO NOTHING";
                entityManager.createNativeQuery(INSERT_LIKE).setParameter(1, like.getUserId())
                        .setParameter(2, like.getPostId()).executeUpdate();
            }
        }
    }

    @Transactional
    @Override
    public void dislike(LikeDTO like) {
        if (like.getPostId() != null && like.getUserId() != null) {
            String DELETE_LIKE = "delete from \"like\" where user_id = ?1 and post_id = ?2";
            entityManager.createNativeQuery(DELETE_LIKE).setParameter(1, like.getUserId())
                    .setParameter(2, like.getPostId()).executeUpdate();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageDTO<PostDTO> getPage(SearchDTO<PostSearch> searchPostDTO) {
        LOGGER.info("Enter to getPage method, in class PostServiceImpl. Search parameters {}", searchPostDTO);
        List<PostDTO> postDTOS = new ArrayList<>();
        for (Object entity : entityManager.createNativeQuery(getPageQuery(searchPostDTO), Post.class).getResultList()) {
            postDTOS.add(postMapper.toPostDto((Post) entity));
        }
        Page<PostDTO> page = new PageImpl<>(postDTOS);
        PageDTO<PostDTO> pageDTO = new PageDTO<>();
        pageDTO.setContent(page.getContent());
        pageDTO.setTotalItem(((BigInteger) entityManager.createNativeQuery(getCountQuery(searchPostDTO)).getSingleResult()).longValue());
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

    private StringBuilder getQuery() {
        return new StringBuilder();
    }

    private String getPageQuery(SearchDTO<PostSearch> search) {
        StringBuilder query = getQuery();
        PostSearch searchPattern = search.getSearchPattern();
        query.append("SELECT * FROM post");
        if (searchPattern != null && searchPattern.getSearch() != null) {
            getFilter(searchPattern,query);
            if (search.getSortDirection() != null && search.getSortField() != null) {
                query.append(" ORDER BY post.").append(search.getSortField()).append(" ").append(search.getSortDirection());
            }
        }
        if (search.getPage() != null && search.getPageSize() != null) {
            query.append(" limit ").append(search.getPageSize()).append(" offset ").append(search.getPage() * search.getPageSize());
        }

        return query.toString();
    }

    private String getCountQuery(SearchDTO<PostSearch> search) {
        StringBuilder query = getQuery();
        PostSearch searchPattern = search.getSearchPattern();
        query.append("SELECT count(*) FROM post");
        if (searchPattern != null && searchPattern.getSearch() != null) {
            getFilter(searchPattern,query);
        }
        return query.toString();
    }

    private void getFilter(PostSearch postSearch, StringBuilder query){
        if (postSearch.getSearchTag() != null) {
            query.append(" Left JOIN post_tag pt on post.id = pt.post_id " +
                    "Left JOIN tag t on pt.tag_id = t.id");
        }
        if (!Objects.equals(postSearch.getSearch(), "")
                || postSearch.getSearchTag() != null) {
            query.append(" WHERE ");
        }
        if (!Objects.equals(postSearch.getSearch(), "")) {
            query.append("post.ts_content @@ phraseto_tsquery('english', '").append(postSearch.getSearch()).append("')");
            query.append(" or post.ts_title @@ phraseto_tsquery('english', '").append(postSearch.getSearch()).append("') ");
        }
        if (!Objects.equals(postSearch.getSearch(), "") && postSearch.getSearchTag() != null) {
            query.append(" or ");
        }
        if (postSearch.getSearchTag() != null) {
            query.append("t.name like '%").append(postSearch.getSearchTag()).append("%'");
        }
    }
}
