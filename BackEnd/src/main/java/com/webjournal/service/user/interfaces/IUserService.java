package com.webjournal.service.user.interfaces;

import com.webjournal.dto.AuthorDTO;

import java.util.List;

public interface IUserService {
    List<AuthorDTO> getInterestingBloggers(int quantity);
}
