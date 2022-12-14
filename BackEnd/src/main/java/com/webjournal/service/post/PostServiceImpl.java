package com.webjournal.service.post;

import com.webjournal.dto.PageDTO;
import com.webjournal.dto.PostDTO;
import com.webjournal.dto.post.PostFormDTO;
import com.webjournal.dto.post.PostListDTO;
import com.webjournal.dto.post.PostPreviewDTO;
import com.webjournal.dto.search.AuthorsPostsSearch;
import com.webjournal.dto.search.PostSearch;
import com.webjournal.dto.search.SearchDTO;
import com.webjournal.entity.Post;
import com.webjournal.entity.User;
import com.webjournal.enums.RoleType;
import com.webjournal.enums.SortDirection;
import com.webjournal.exception.DatabaseFetchException;
import com.webjournal.mapper.PostMapper;
import com.webjournal.repository.PostRepository;
import com.webjournal.service.fileStorage.FilesStorageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class PostServiceImpl implements PostService {
    private final PostRepository repository;
    private final PostMapper postMapper;
    private final EntityManager entityManager;
    private final FilesStorageServiceImpl fileService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);

    public PostServiceImpl(PostRepository repository, PostMapper postMapper, EntityManager entityManager, FilesStorageServiceImpl fileService) {
        this.repository = repository;
        this.postMapper = postMapper;
        this.entityManager = entityManager;
        this.fileService = fileService;
    }

    @Override
    public Integer create(PostFormDTO dto) {
        Post createdPost = postMapper.createToEntity(new Post(), dto);
        return repository.save(createdPost).getId();
    }

    @Override
    public void delete(Integer id) throws IOException {
        fileService.delete("PostImage/post_" + id + ".jpg");
        repository.deleteById(id);
    }

    @Override
    public void update(PostDTO dto) {
        Post postToUpdate = repository.findById(dto.getId()).orElseThrow(() -> new DatabaseFetchException("Could not find Post entity with id " + dto.getId()));
        Post updatedPost = postMapper.toEntity(postToUpdate, dto);
        repository.save(updatedPost);
    }

    @Override
    public void updateWithPhoto(PostDTO dto) throws IOException {
        Post postToUpdate = repository.findById(dto.getId()).orElseThrow(() -> new DatabaseFetchException("Could not find Post entity with id " + dto.getId()));
        Post updatedProduct = postMapper.toEntity(postToUpdate, dto);
        fileService.delete("PostImage/post_" + dto.getId() + ".jpg");
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

    @Override
    public List<PostListDTO> getSimilarPosts(Integer postId) {
        Pageable page = PageRequest.of(0, 3);
        return repository.findSimilarTags(page, postId).stream().map(postMapper::toPostListDto).toList();
    }

    @Transactional
    @Override
    public void like(Integer postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authorizeUser = (User)authentication.getPrincipal();
        if (postId != null ) {
            Integer authorId = (Integer) entityManager.createNativeQuery("SELECT author_id from post where id = ?1")
                    .setParameter(1, postId)
                    .getSingleResult();
            if (!Objects.equals(authorId, authorizeUser.getId())) {
                Boolean isExist = (Boolean) entityManager.createNativeQuery("select exists(select 1 from \"like\" where user_id=?1 and post_id=?2)")
                        .setParameter(1, authorizeUser.getId())
                        .setParameter(2, postId)
                        .getSingleResult();
                if (!isExist) {
                    String INSERT_LIKE = "insert into \"like\"(user_id, post_id) VALUES (?1,?2) ON CONFLICT DO NOTHING";
                    entityManager.createNativeQuery(INSERT_LIKE).setParameter(1, authorizeUser.getId())
                            .setParameter(2, postId).executeUpdate();
                }
            }
        }
    }

    @Transactional
    @Override
    public void dislike(Integer postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authorizeUser = (User)authentication.getPrincipal();
        if (postId != null) {
            String DELETE_LIKE = "delete from \"like\" where user_id = ?1 and post_id = ?2";
            entityManager.createNativeQuery(DELETE_LIKE).setParameter(1, authorizeUser.getId())
                    .setParameter(2, postId).executeUpdate();
        }
    }

    @Transactional
    @Override
    public Boolean approved(Integer postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authorizeUser = (User)authentication.getPrincipal();
        Post post = repository.findById(postId).orElseThrow(() -> new DatabaseFetchException("Could not find Post entity with id " + postId));
        if (!Objects.equals(post.getAuthor().getId(), authorizeUser.getId())){
            post.setApproved(true);
            post.setPublishedAt(LocalDateTime.now());
            repository.save(post);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public Boolean canselApproved(Integer postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authorizeUser = (User)authentication.getPrincipal();
        Post post = repository.findById(postId).orElseThrow(() -> new DatabaseFetchException("Could not find Post entity with id " + postId));
        if (!Objects.equals(post.getId(), authorizeUser.getId())){
            post.setApproved(false);
            repository.save(post);
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PageDTO<PostListDTO> getPage(SearchDTO<PostSearch> searchPostDTO) {
        LOGGER.info("Enter to getPage method, in class PostServiceImpl. Search parameters {}", searchPostDTO);
        List<PostListDTO> postDTOS = new ArrayList<>();
        for (Object entity : entityManager.createNativeQuery(getPageQuery(searchPostDTO), Post.class).getResultList()) {
            postDTOS.add(postMapper.toPostListDto((Post) entity));
        }
        Page<PostListDTO> page = new PageImpl<>(postDTOS);
        PageDTO<PostListDTO> pageDTO = new PageDTO<>();
        pageDTO.setContent(page.getContent());
        pageDTO.setTotalItem(((BigInteger) entityManager.createNativeQuery(getCountQuery(searchPostDTO)).getSingleResult()).longValue());
        return pageDTO;
    }

    /**
     * ?????? ????????????????????, ??????????????????, ?? ???????????? ?????????????? ?? ???????????????????? Native Query, ?????????? ???? ???? ?????????? ???????? ??????????????????????,
     * fullTextSearch. Hibernate Search ?? ???? ???????? ?????????????????????? ?????????? ????, ???? ?????? ???????????????????????? ???????? ?????? ??????????????????, ????????
     * ???? ???????????????? ?????? ???????????????????? ?????????????????? ?????????? ??????????????????????. ?????????? ?? criteriaBuilder ?????????? ?????????????????????? ??????????????, ?????? ??
     * ?????????????????? ???????????????? ??????????????????. ???????? ???? ?? ???????? ?????????????????????? ???????? ?????????????????? ???????????????? ??????????.
     *
     * @see com.webjournal.utils.FullTextSearchPred
     */

    private String getPageQuery(SearchDTO<PostSearch> search) {
        StringBuilder query = new StringBuilder();
        PostSearch searchPattern = new PostSearch();
        if (search.getSearchPattern() != null) {
            searchPattern = search.getSearchPattern();
        }
        query.append("SELECT * FROM post p");
        if (searchPattern != null && searchPattern.getSearch() != null) {
            query.append(getWhere(searchPattern));
            if (search.getSortDirection() != null && search.getSortField() != null) {
                query.append(" ORDER BY p.").append(search.getSortField()).append(" ").append(search.getSortDirection());
            }
        }
        if (search.getPage() != null && search.getPageSize() != null) {
            query.append(" limit ").append(search.getPageSize()).append(" offset ").append(search.getPage() * search.getPageSize());
        }

        return query.toString();
    }

    private String getCountQuery(SearchDTO<PostSearch> search) {
        StringBuilder query = new StringBuilder();
        PostSearch searchPattern = search.getSearchPattern();
        query.append("SELECT count(*) FROM post p");
        if (searchPattern != null && searchPattern.getSearch() != null) {
            query.append(getWhere(searchPattern));
        }
        return query.toString();
    }

    private StringBuilder getWhere(PostSearch postSearch) {
        StringBuilder query = new StringBuilder();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authorizeUser = null;
        if (authentication.getPrincipal() != "anonymousUser") {
            authorizeUser = (User) authentication.getPrincipal();
        }
        query.append(" WHERE ");
        boolean apr = true;
        if (authorizeUser == null || authorizeUser.getRole().getRole() == RoleType.AUTHOR || postSearch.getIsApprove() == null) {
            apr = true;
        } else if (postSearch.getIsApprove() != null) {
            apr = postSearch.getIsApprove();
        }
        query.append(" is_approved = ").append(apr).append(' ');
        if (postSearch.getSearchTag() != null || postSearch.getSearch() != null) {
            if (!Objects.equals(postSearch.getSearch(), "") || !Objects.equals(postSearch.getSearchTag(), "")) {
                query.append(" and (");
            }
            if (!Objects.equals(postSearch.getSearch(), "")) {
                query.append("p.ts_content @@ phraseto_tsquery('english', '").append(postSearch.getSearch()).append("')");
                query.append(" or p.ts_title @@ phraseto_tsquery('english', '").append(postSearch.getSearch()).append("') ");
            }
            if (!Objects.equals(postSearch.getSearch(), "") && !Objects.equals(postSearch.getSearchTag(), "")) {
                query.append(" or ");
            }
            if (!Objects.equals(postSearch.getSearchTag(), "")) {
                query.append(" array_to_string(array(select name from tag left join post_tag pt on pt.tag_id = tag.id where pt.post_id = p.id),',') like '%").append(postSearch.getSearchTag()).append("%'");
            }
            if (!Objects.equals(postSearch.getSearch(), "") || !Objects.equals(postSearch.getSearchTag(), "")) {
                query.append(")");
            }
        }
        return query;
    }

    @Override
    public List<PostListDTO> getNewPost(SearchDTO search) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() == "anonymousUser") {
            return null;
        }
        User authorizeUser = (User) authentication.getPrincipal();
        Integer userId = authorizeUser.getId();
        Sort sort = Sort.by("published_at");
        Pageable pageable = PageRequest.of(search.getPage(), search.getPageSize(), sort);
        return repository.findNewsPosts(pageable, userId).stream().map(postMapper::toPostListDto).toList();
    }

    @Override
    public PageDTO<PostPreviewDTO> getAuthorsPosts(SearchDTO<AuthorsPostsSearch> search, Integer authorId) {
        Sort sort = search.getSortDirection() == SortDirection.DESC
                ? Sort.by(search.getSortField()).descending()
                : Sort.by(search.getSortField()).ascending();
        Pageable pageable = PageRequest.of(search.getPage(), search.getPageSize(), sort);
        Page<Post> pages;
        if (search.getSearchPattern().getAreApproved() == null) {
            pages = repository.findAllAuthorsPosts(pageable, authorId);
        }
        else pages = repository.findAuthorsPosts(pageable, authorId, search.getSearchPattern().getAreApproved());
        PageDTO<PostPreviewDTO> pageDTO = new PageDTO<>();
        pageDTO.setContent(pages.getContent().stream().map(postMapper::toPostPreviewDTO).toList());
        pageDTO.setTotalItem(pages.getTotalElements());
        return pageDTO;
    }
}
