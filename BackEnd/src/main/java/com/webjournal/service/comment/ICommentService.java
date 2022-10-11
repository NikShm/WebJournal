package com.webjournal.service.comment;

import com.webjournal.dto.CommentDTO;
import com.webjournal.dto.PageDTO;
import com.webjournal.dto.search.SearchAuthorDTO;
import com.webjournal.dto.search.SearchCommentDTO;

import java.io.IOException;

public interface ICommentService {
    Integer create(CommentDTO dto);

    void delete(Integer id) throws IOException;

    void update(CommentDTO dto) throws IOException;

    PageDTO<CommentDTO> getPage(SearchCommentDTO search);
}
