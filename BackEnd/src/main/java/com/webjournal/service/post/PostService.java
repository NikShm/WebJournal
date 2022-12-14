package com.webjournal.service.post;

import com.webjournal.dto.PageDTO;
import com.webjournal.dto.PostDTO;
import com.webjournal.dto.post.PostFormDTO;
import com.webjournal.dto.post.PostListDTO;
import com.webjournal.dto.post.PostPreviewDTO;
import com.webjournal.dto.search.AuthorsPostsSearch;
import com.webjournal.dto.search.PostSearch;
import com.webjournal.dto.search.SearchDTO;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface PostService {
    List<PostListDTO> getSimilarPosts(Integer postId);
    void like(Integer postId);
    void dislike(Integer postId);
    @Transactional
    Boolean approved(Integer postId);
    @Transactional
    Boolean canselApproved(Integer postId);
    PageDTO<PostListDTO> getPage(SearchDTO<PostSearch> searchDTO);
    Integer create(PostFormDTO dto);
    void delete(Integer id) throws IOException;
    void update(PostDTO dto);
    void updateWithPhoto(PostDTO dto) throws IOException;
    PostDTO get(Integer id);
    List<PostDTO> getAll();
    List<PostListDTO> getFeaturedPosts(Integer quantity, LocalDateTime date);
    List<PostListDTO> getNewPost(SearchDTO<PostSearch> search);
    PageDTO<PostPreviewDTO> getAuthorsPosts(SearchDTO<AuthorsPostsSearch> search, Integer authorId);
}
