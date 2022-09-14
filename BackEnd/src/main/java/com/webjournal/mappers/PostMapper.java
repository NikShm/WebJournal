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
@class PostMapper
@version 1.0.0
@since 05.09.2022 - 19.44
*/
@Component
public class PostMapper {
    public PostDTO toDto(Post book) {
        PostDTO dto = new PostDTO(book);
        List<AuthorDTO>authorList = new ArrayList<>();
        for (Author author : book.getAuthorList()){
            AuthorDTO authorDTO = new AuthorDTO(author);
            authorList.add(authorDTO);
        }
        System.out.println(book.getAuthorList());
        dto.setAuthorList(authorList);
        return dto;
    }

}
