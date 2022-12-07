package com.webjournal.service.comment;

import com.webjournal.dto.CommentDTO;
import com.webjournal.dto.PageDTO;
import com.webjournal.dto.search.CommentSearch;
import com.webjournal.dto.search.SearchDTO;

import java.io.IOException;

public interface ICommentService {
    Integer create(CommentDTO dto);

    void delete(Integer id) throws IOException;

    void update(CommentDTO dto) throws IOException;

    PageDTO<CommentDTO> getPage(SearchDTO<CommentSearch>  search);
}
