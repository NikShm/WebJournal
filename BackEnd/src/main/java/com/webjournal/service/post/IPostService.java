package com.webjournal.service.post;

import com.webjournal.dto.LikeDTO;
import com.webjournal.dto.PageDTO;
import com.webjournal.dto.PostDTO;
import com.webjournal.dto.search.SearchPostDTO;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface IPostService{
    void like(LikeDTO like);
    void dislike(LikeDTO like);
    PageDTO<PostDTO> getPage(SearchPostDTO searchDTO);
    Integer create(PostDTO dto);
    void delete(Integer id) throws IOException;
    void update(PostDTO dto) throws IOException;
    PostDTO get(Integer id);
    List<PostDTO> getAll();
    List<PostDTO> getFeaturedPosts(int quantity, LocalDateTime date);
}
