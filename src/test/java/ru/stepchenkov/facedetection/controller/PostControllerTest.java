package ru.stepchenkov.facedetection.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.stepchenkov.facedetection.api.ApiControllerTest;
import ru.stepchenkov.rest.entity.Post;
import ru.stepchenkov.rest.exception.PostAlreadyExistsException;
import ru.stepchenkov.rest.service.PostService;

public class PostControllerTest extends ApiControllerTest {

    @MockBean
    private PostService postService;

    private Post post;

    @BeforeEach
    public void init() {
        post = new Post();
        post.setPostName("test");
    }

    @Test
    @DisplayName("save post in database")
    void saveTest() throws Exception {
        BDDMockito.given(postService.save(ArgumentMatchers.any()))
                .willAnswer(invocation -> invocation.getArgument(0));
        String json = getObjectMapper().writeValueAsString(post);

        getMockMvc().perform(MockMvcRequestBuilders.post("/post/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("delete post from database")
    void deleteTest() throws Exception {
        getMockMvc().perform(MockMvcRequestBuilders.delete("/post/delete/{value}", "test"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
