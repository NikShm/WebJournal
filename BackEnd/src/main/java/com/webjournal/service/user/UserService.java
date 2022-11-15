package com.webjournal.service.user;

import com.webjournal.dto.FollowDTO;
import com.webjournal.dto.PageDTO;
import com.webjournal.dto.search.AuthorSearch;
import com.webjournal.dto.search.SearchDTO;
import com.webjournal.dto.user.AuthorDTO;
import com.webjournal.dto.user.UserDTO;
import com.webjournal.security.payload.request.RegistrationRequest;
import freemarker.template.TemplateException;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface UserService {
    UserDTO getByUsername(String username);
    Boolean checkIfUserExistsByUsername(String username);
    Boolean checkIfUserExistsByEmail(String email);
    void create(RegistrationRequest registrationRequest) throws TemplateException, MessagingException, IOException;
    void verifyUser(String token);
    void delete(Integer id);
    //void update(UserDTO dto);
    UserDTO getById(Integer id);
    List<UserDTO> getAll();
    List<AuthorDTO> getInterestingAuthors(int quantity);
    @Transactional
    void subscribe(FollowDTO followDTO);
    @Transactional
    void unsubscribe(FollowDTO followDTO);
    PageDTO<AuthorDTO> getAuthorPage(SearchDTO<AuthorSearch> search);
}
