package com.webjournal.WebJournal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webjournal.dto.TagDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
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
 * @class TagControllerTest
 * @since 10/4/2022 - 11.22
 **/
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class TagControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    @Sql(value = "/actual-tags/create-tags-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/actual-tags/delete-tags-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetActualTags() throws Exception {
        List<TagDTO> actualTags = Arrays.asList(
                new TagDTO(1, "ui", LocalDateTime.of(2022,10,15,19,10,25)),
                new TagDTO(4, "java", LocalDateTime.of(2022,10,13,19,10,25)),
                new TagDTO(6, "cooking", LocalDateTime.of(2022,10,16,19,10,25))
        );

        mockMvc.perform(get("/api/tags/actual").param("count", "3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(objectMapper.writeValueAsString(actualTags)))
                .andDo(print());
    }
}
