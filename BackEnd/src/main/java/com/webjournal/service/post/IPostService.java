package com.webjournal.service.post;

import com.webjournal.dto.LikeDTO;
import com.webjournal.dto.PageDTO;
import com.webjournal.dto.PostDTO;
import com.webjournal.dto.search.PostSearch;
import com.webjournal.dto.search.SearchDTO;
import com.webjournal.dto.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface IPostService{
    void like(LikeDTO like);
    void dislike(LikeDTO like);
    PageDTO<PostDTO> getPage(SearchDTO<PostSearch> searchDTO);
    Integer create(PostDTO dto);
    void delete(Integer id) throws IOException;
    void update(PostDTO dto) throws IOException;
    PostDTO get(Integer id);
    List<PostDTO> getAll();
    List<PostListDTO> getFeaturedPosts(Integer quantity, LocalDateTime date);
}
