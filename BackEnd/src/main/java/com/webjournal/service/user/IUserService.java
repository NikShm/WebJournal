package com.webjournal.service.user;

import com.webjournal.dto.user.AuthorDTO;

import java.util.List;

public interface IUserService {
    List<AuthorDTO> getInterestingAuthors(int quantity);
}
