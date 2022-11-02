package com.webjournal.service.user;

import com.webjournal.dto.FollowDTO;
import com.webjournal.dto.PageDTO;
import com.webjournal.dto.SearchDTO;
import com.webjournal.dto.user.AuthorDTO;
import com.webjournal.dto.user.UserDTO;
import com.webjournal.entity.User;
import com.webjournal.security.payload.request.RegistrationRequest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

public interface UserService {
    Boolean checkIfUserExistsByUsername(String username);

    Boolean checkIfUserExistsByEmail(String email);

    void create(RegistrationRequest registrationRequest);

    void sendRegistrationConfirmationEmail(User user);

    boolean verifyUser(String token);

    void delete(Integer id) throws IOException;
    void update(UserDTO dto) throws IOException;
    UserDTO get(Integer id);
    List<UserDTO> getAll();
    List<AuthorDTO> getInterestingAuthors(int quantity);

    @Transactional
    void subscribe(FollowDTO followDTO);

    @Transactional
    void unsubscribe(FollowDTO followDTO);

    PageDTO<AuthorDTO> getPage(SearchDTO search);
}
