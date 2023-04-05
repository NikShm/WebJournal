package com.webjournal.WebJournal.controller;

import com.webjournal.dto.post.PostFormDTO;
import com.webjournal.dto.post.PostListDTO;
import com.webjournal.utils.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    @Sql(value = {"/users/create-users-before.sql", "/posts/create-posts-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/posts/delete-posts-after.sql", "/users/delete-users-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetFeaturedPosts() throws Exception {
        LocalDateTime date1 = LocalDateTime.of(2022,10,15,19,10,25);
        LocalDateTime date2 = LocalDateTime.of(2022,10,16,19,10,25);
        LocalDateTime date3 = LocalDateTime.of(2022,10,13,19,10,25);
        LocalDateTime date4 = LocalDateTime.of(2022,10,14,19,10,25);
        List<PostListDTO> featuredPosts = Arrays.asList(
                new PostListDTO(8, "Guide for Pairing Fonts", "UI", date1),
                new PostListDTO(1, "Sesame Apricot Tofu", "Cooking", date2),
                new PostListDTO(5, "Java Stream API", "Programming", date3),
                new PostListDTO(6, "Custom Exceptions", "Programming", date4)
        );

        mockMvc.perform(get("/api/posts/top-per-month").param("count", "4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(JsonUtil.toJson(featuredPosts)))
                .andDo(print());
    }

    @Test
    public void testCreateShouldReturnUnauthorized() throws Exception {
        PostFormDTO newPost = new PostFormDTO("My post", "Preview", "Content", null);

        mockMvc.perform(post("/api/posts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(newPost)))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value("401"))
                .andExpect(jsonPath("$.error").value("Unauthorized"))
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.message").value("Full authentication is required to access this resource"));
    }

    @Test
    @WithMockUser(username = "yuliana@chnu.edu.ua", password = "$2a$10$HzGSQ/C.BsbmjQjQIQRX9eU6Vie2C2.9TCvuo7MNPLJNNWJz04wZu", roles = "AUTHOR")
    @Sql(value = {"/users/create-users-before.sql", "/posts/create-post-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/posts/delete-posts-after.sql", "/users/delete-users-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testDeleteOne() throws Exception {
        mockMvc.perform(delete("/api/posts/1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
