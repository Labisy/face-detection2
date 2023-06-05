package ru.stepchenkov.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stepchenkov.rest.entity.Post;
import ru.stepchenkov.rest.exception.PostAlreadyExistsException;
import ru.stepchenkov.rest.exception.PostNotFoundException;
import ru.stepchenkov.rest.model.PostModel;
import ru.stepchenkov.rest.repo.PostRepo;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;

    public Post save(Post post) throws PostAlreadyExistsException {
        if (postRepo.getPostByPostName(post.getPostName()) != null)
            throw new PostAlreadyExistsException("Такой post уже существует");
        return postRepo.save(post);
    }

    public String delete(String postName) throws PostNotFoundException {
        if (postRepo.getPostByPostName(postName) == null)
            throw new PostNotFoundException("Такого пользователя не существует");
        return postRepo.deleteByPostName(postName);
    }

    public List<PostModel> getAll() {
        List<Post> posts = (List<Post>) postRepo.findAll();
        return posts.stream().map(PostModel::toModel).toList();
    }
}
