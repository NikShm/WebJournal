package com.webjournal.WebJournal.controller;

import com.webjournal.controller.user.UserRestController;
import com.webjournal.dto.user.AuthorDTO;
import com.webjournal.entity.User;
import com.webjournal.service.user.impls.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class UserControllerTest
 * @since 9/27/2022 - 10.41
 **/
@WebMvcTest(UserRestController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceImpl userService;

    @Test
    public void getInterestingAuthorsTest() throws Exception {
        User user1 = new User(1, "user1", "password1", "user1@gmail.com",
                LocalDate.of(2000, 2, 5), "", null, null, null, null,
                LocalDateTime.now(), LocalDateTime.now());
        User user2 = new User(2, "user2", "password2", "user2@gmail.com",
                LocalDate.of(2000, 2, 5), "", null, null, null, null,
                LocalDateTime.now(), LocalDateTime.now());
        User user3 = new User(3, "user3", "password3", "user3@gmail.com",
                LocalDate.of(2000, 2, 5), "", null, null, null, null,
                LocalDateTime.now(), LocalDateTime.now());

        user2.setFollowers(Set.of(user1, user3));
        user1.setFollowers(Set.of(user2));

        List<AuthorDTO> interestingAuthors = Arrays.asList(
                new AuthorDTO(2, "user2", 2),
                new AuthorDTO(1, "user1", 1),
                new AuthorDTO(3, "user3", 0)
        );

        when(userService.getInterestingAuthors(3)).thenReturn(interestingAuthors);

        mockMvc.perform(get("/api/users/top?count=3").contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":  2, \"username\": \"user2\", \"followers\": 2}," +
                        "{\"id\":  1, \"username\": \"user1\", \"followers\": 1}," +
                        "{\"id\":  3, \"username\": \"user3\", \"followers\": 0}]"))
                .andDo(print());
    }
}
