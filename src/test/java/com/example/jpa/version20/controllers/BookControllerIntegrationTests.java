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
import com.example.jpa.version20.domain.entities.BookEntity;
import com.example.jpa.version20.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {
        private MockMvc mockMvc;

        private ObjectMapper objectMapper;

        private BookService bookService;

        @Autowired
        public BookControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, BookService bookService) {
                this.mockMvc = mockMvc;
                this.objectMapper = objectMapper;
                this.bookService = bookService;
        }

        @Test
        public void testThatCreateBookSuccessfullyReturnsHttpStatus201Created() throws Exception {
                BookEntity tesBookEntity = TestDataUtil.createTestBookA(null);
                String isbn = tesBookEntity.getIsbn();
                String bookJson = objectMapper.writeValueAsString(tesBookEntity);
                mockMvc.perform(
                                MockMvcRequestBuilders.put("/books/{isbn}", isbn)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .content(bookJson))
                                .andExpect(
                                                MockMvcResultMatchers.status().isCreated())
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.isbn").value(isbn))
                                .andExpect(
                                                MockMvcResultMatchers.jsonPath("$.title")
                                                                .value(tesBookEntity.getTitle()));
        }

        @Test
        public void testThatFindAllBooksReturnsListandHttpStatus20OOK() throws Exception {
                BookEntity bookEntity1 = TestDataUtil.createTestBookA(null);
                String isbn1 = bookEntity1.getIsbn();
                bookService.createBook(isbn1, bookEntity1);
                BookEntity bookEntity2 = TestDataUtil.createTestBookB(null);
                String isbn2 = bookEntity2.getIsbn();
                bookService.createBook(isbn2, bookEntity2);
                mockMvc.perform(
                                MockMvcRequestBuilders.get("/books"))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));

        }

        @Test
        public void testThatFindBookByIsbnReturnsBookAndHttpStatus200() throws Exception {
                BookEntity bookEntity = TestDataUtil.createTestBookA(null);
                String isbn = bookEntity.getIsbn();
                bookService.createBook(isbn, bookEntity);
                mockMvc.perform(
                                MockMvcRequestBuilders.get("/books/{isbn}", isbn))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(isbn));
        }

        @Test
        public void testThatDeleteBookByIsbnIsExecutedAndReturnsHttpStatus200() throws Exception {
                BookEntity bookEntity = TestDataUtil.createTestBookA(null);
                String isbn = bookEntity.getIsbn();
                bookService.createBook(isbn, bookEntity);
                mockMvc.perform(
                                MockMvcRequestBuilders.delete("/books/{isbn}", isbn))
                                .andExpect(MockMvcResultMatchers.status().isOk())
                                .andExpect(MockMvcResultMatchers.content().string("Book deleted"));
        }
}
