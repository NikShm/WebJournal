package com.webjournal.mappers;


import com.webjournal.dto.AuthorDTO;
import com.webjournal.dto.PostDTO;
import com.webjournal.entities.Author;
import com.webjournal.entities.Post;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*
@author Микола
@project High_school_library
@class UserMapper
@version 1.0.0
@since 05.09.2022 - 19.45
*/
@Component
public class UserMapper {
    public AuthorDTO toDto(Author author) {
        AuthorDTO dto = new AuthorDTO(author);
        List<PostDTO> postList = new ArrayList<>();
        for (Post book : author.getBooks()){
            PostDTO bookDTO = new PostDTO(book);
            postList.add(bookDTO);
        }
        dto.setBooks(postList);
        return dto;
    }

    public Author toEntity(AuthorDTO dto) {
        return new Author();
    }
}
