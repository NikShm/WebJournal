package com.webjournal.service.post;

import com.webjournal.dto.LikeDTO;
import com.webjournal.dto.PageDTO;
import com.webjournal.dto.PostDTO;
import com.webjournal.dto.SearchPostDTO;
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
import java.io.IOException;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PostServiceImpl implements IPostService{
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
    public List<PostDTO> getFeaturedPosts(int quantity, LocalDateTime date) {
        Pageable page = PageRequest.of(0, quantity);
        return repository.findInterestingPosts(page, date).stream().map(postMapper::toPostDto).toList();
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
    public PageDTO<PostDTO> getPage(SearchPostDTO searchPostDTO) {
        LOGGER.info("Enter to getPage method, in class PostServiceImpl. Search parameters {}",searchPostDTO);
        List<PostDTO> postDTOS = new ArrayList<>();
        for (Object entity : entityManager.createNativeQuery(getQuery(searchPostDTO), Post.class).getResultList()) {
            postDTOS.add(postMapper.toPostDto((Post) entity));
        }
        Page<PostDTO> page = new PageImpl<>(postDTOS);
        PageDTO<PostDTO> pageDTO = new PageDTO<>();
        pageDTO.setContent(page.getContent());
        pageDTO.setTotalItem(((BigInteger)entityManager.createNativeQuery("SELECT count(*) from post").getSingleResult()).longValue());
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
    private String getQuery(SearchPostDTO searchPost) {
        StringBuilder query = new StringBuilder("SELECT * FROM post");
        if (searchPost.getSearchTag() != null) {
            query.append(" INNER JOIN post_tag pt on post.id = pt.post_id " +
                    "INNER JOIN tag t on pt.tag_id = t.id");
        }
        if (searchPost.getSearch() != null || searchPost.getSearchTag() != null){
            query.append(" WHERE ");
        }
        if (searchPost.getSearch() != null && !Objects.equals(searchPost.getSearch(), "")) {
            query.append("post.ts_content @@ phraseto_tsquery('english', '").append(searchPost.getSearch()).append("')");
            query.append(" or post.ts_title @@ phraseto_tsquery('english', '").append(searchPost.getSearch()).append("')");
        }
        if (searchPost.getSearchTag() != null){
            query.append(" or t.name like '%").append(searchPost.getSearchTag()).append("%'");
        }
        if (searchPost.getSortDirection() != null && searchPost.getSortField() != null) {
            query.append(" ORDER BY ").append(searchPost.getSortField()).append(" ").append(searchPost.getSortDirection());
        }
        if (searchPost.getPage() != null && searchPost.getPageSize() != null) {
            query.append(" limit ").append(searchPost.getPageSize()).append(" offset ").append(searchPost.getPage());
        }
        return query.toString();
    }
}
