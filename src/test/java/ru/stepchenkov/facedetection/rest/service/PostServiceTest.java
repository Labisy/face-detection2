package ru.stepchenkov.facedetection.rest.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ru.stepchenkov.rest.entity.Post;
import ru.stepchenkov.rest.exception.PostAlreadyExistsException;
import ru.stepchenkov.rest.exception.PostNotFoundException;
import ru.stepchenkov.rest.repo.PostRepo;
import ru.stepchenkov.rest.service.PostService;

class PostServiceTest {

    @InjectMocks
    private PostService postService;
    @Mock
    private PostRepo postRepo;
    private Post post;

    public PostServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void init() {
        post = new Post();
        post.setPostName("test");
    }

    @Test
    @DisplayName("save post data in database")
    void saveTest() throws PostAlreadyExistsException {
        Mockito.when(postRepo.save(ArgumentMatchers.any())).thenReturn(post);
        Post actual = postService.save(post);

        Assertions.assertThat(actual)
                .isNotNull()
                .extracting(Post::getPostName)
                .isEqualTo("test");

    }

    @Test
    @DisplayName("delete post from database")
    void deleteTest() throws PostNotFoundException {
       Mockito.when(postRepo.getPostByPostName(ArgumentMatchers.any())).thenReturn(post);
       Mockito.when(postRepo.deleteByPostName(ArgumentMatchers.any())).thenReturn(post.getPostName());

       String actual = postService.delete("name");

       Assertions.assertThat(actual)
               .isNotNull()
               .isEqualTo(post.getPostName());
    }
}
