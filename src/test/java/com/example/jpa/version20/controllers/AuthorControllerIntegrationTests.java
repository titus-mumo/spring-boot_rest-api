package com.example.jpa.version20.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.jpa.version20.TestDataUtil;
import com.example.jpa.version20.domain.entities.AuthorEntity;
import com.example.jpa.version20.services.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {

        private MockMvc mockMvc;

        private ObjectMapper objectMapper;

        private AuthorService authorService;

        @Autowired
        public AuthorControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper,
                        AuthorService authorService) {
                this.mockMvc = mockMvc;
                this.objectMapper = objectMapper;
                this.authorService = authorService;
        }

        @Test
        public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
                AuthorEntity testAuthor = TestDataUtil.createTestAuthorA();
                testAuthor.setId(null);
                String authorJson = objectMapper.writeValueAsString(testAuthor);
                mockMvc.perform(
                                MockMvcRequestBuilders.post("/authors")
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(authorJson))
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.id").isNumber())
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.name").value(testAuthor.getName()))
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.age").value(80));
        }

        @Test
        public void testThatRecallAuthoursSuccessfullyRetursHttpStatus200OK() throws Exception {
                AuthorEntity testAuthorEntity = TestDataUtil.createTestAuthorA();
                authorService.createAuthor(testAuthorEntity);
                mockMvc.perform(MockMvcRequestBuilders.get("/authors"))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.[0].id").isNumber())
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.[0].name")
                                                                .value(testAuthorEntity.getName()))
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.[0].age").value(80));
        }

        @Test
        public void testThatGetAuthorByIdSuccessfullyReturnsAuthor() throws Exception {
                AuthorEntity testAuthor = TestDataUtil.createTestAuthorA();
                AuthorEntity createdAuthor = authorService.createAuthor(testAuthor);
                Long id = createdAuthor.getId();
                mockMvc.perform(MockMvcRequestBuilders.get("/authors/{id}", id))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.id").isNumber())
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.name")
                                                                .value(testAuthor.getName()))
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.age").value(80));
        };

        @Test
        public void testThatDeleteAuthorSuccessfullyReturnsHttpStatus200OK() throws Exception {
                AuthorEntity testAuthor = TestDataUtil.createTestAuthorA();
                AuthorEntity createdAuthor = authorService.createAuthor(testAuthor);
                Long id = createdAuthor.getId();
                mockMvc.perform(MockMvcRequestBuilders.delete("/authors/{id}", id))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().string("Author deleted"));

        }

        @Test
        public void testThatFullUpdatedAuthorReturnsHttpStatus404WhenNoAuthorExists() throws Exception {
                AuthorEntity testAuthor = TestDataUtil.createTestAuthorA();
                String authorJson = objectMapper.writeValueAsString(testAuthor);
                Long id = 33L;
                mockMvc.perform(
                                MockMvcRequestBuilders.put("/author/{id}", id)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(authorJson))
                                .andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        public void testThatSuccessfulFullUpdateReturnsHttpStatus200OK() throws Exception {
                AuthorEntity testAuthor = TestDataUtil.createTestAuthorA();
                AuthorEntity createdAuthor = authorService.createAuthor(testAuthor);
                Long id = createdAuthor.getId();
                createdAuthor.setName("Saki");
                createdAuthor.setAge(24);
                String authorJson = objectMapper.writeValueAsString(createdAuthor);
                mockMvc.perform(
                                MockMvcRequestBuilders.put("/authors/{id}", id)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(authorJson))
                                .andExpect(
                                                MockMvcResultMatchers.status().isOk())
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.name").value("Saki"))
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.age").value(24));

        }

}
