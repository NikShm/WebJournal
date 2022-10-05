package com.webjournal.WebJournal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webjournal.dto.PostDTO;
import com.webjournal.dto.user.AuthorDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Yuliana
 * @version 1.0.0
 * @project WebJournal
 * @class PostControllerTest
 * @since 10/4/2022 - 16.03
 **/
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    @Sql(value = "/featured-posts/create-posts-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/featured-posts/delete-posts-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getFeaturedPostsTest() throws Exception {
        LocalDateTime date1 = LocalDateTime.of(2022,9,16,19,10,25);
        LocalDateTime date2 = LocalDateTime.of(2022,9,1,19,10,25);
        LocalDateTime date3 = LocalDateTime.of(2022,9,13,19,10,25);
        LocalDateTime date4 = LocalDateTime.of(2022,9,14,19,10,25);
        List<PostDTO> featuredPosts = Arrays.asList(
                new PostDTO(1, new AuthorDTO(1, "yulianabilak", 0), "Sesame Apricot Tofu",
                        "Cooking", "Cooking", 6, new ArrayList<>(), new ArrayList<>(), true, date1, date2, date2),
                new PostDTO(5, new AuthorDTO(6, "yuliana", 0), "Logo Design", "UI",
                        "UI", 5, new ArrayList<>(), new ArrayList<>(), true, date3, date2, date2),
                new PostDTO(4, new AuthorDTO(3, "yulianabil", 0), "Custom Exceptions", "Programming",
                        "Programming", 4, new ArrayList<>(), new ArrayList<>(), true, date4, date2, date2),
                new PostDTO(2, new AuthorDTO(1, "yulianabilak", 0), "Awesome Broccoli Cheese Soup",
                        "Cooking", "Cooking", 3, new ArrayList<>(), new ArrayList<>(), true, date1, date2, date2)
        );

        mockMvc.perform(get("/api/posts/top-per-month").param("count", "4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(objectMapper.writeValueAsString(featuredPosts)))
                .andDo(print());
    }
}
