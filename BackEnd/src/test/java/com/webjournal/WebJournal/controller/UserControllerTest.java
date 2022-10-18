package com.webjournal.WebJournal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webjournal.dto.user.AuthorDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

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
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Sql(value = "/interesting-authors/create-users-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/interesting-authors/delete-users-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetInterestingAuthors() throws Exception {
        List<AuthorDTO> interestingAuthors = Arrays.asList(new AuthorDTO(1, "yulianabilak", 6),
                                                        new AuthorDTO(3, "yulianabil", 5),
                                                        new AuthorDTO(4, "yulianabi", 4));

        mockMvc.perform(get("/api/users/top").param("count", "3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(objectMapper.writeValueAsString(interestingAuthors)))
                .andDo(print());
    }
}
