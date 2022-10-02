package com.webjournal.service.post;

import com.webjournal.dto.PageDTO;
import com.webjournal.dto.PostDTO;
import com.webjournal.dto.SearchDTO;
import com.webjournal.entity.Post;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public interface IPostService{
    PageDTO<PostDTO> getPage(SearchDTO searchDTO);
    Integer create(PostDTO dto);
    void delete(Integer id) throws IOException;
    void update(PostDTO dto) throws IOException;
    PostDTO get(Integer id);
    List<PostDTO> getAll();
    List<PostDTO> getInterestingPosts(int quantity, LocalDateTime date);
}
