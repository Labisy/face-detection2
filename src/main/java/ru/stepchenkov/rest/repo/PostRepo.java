package ru.stepchenkov.rest.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.stepchenkov.rest.entity.Post;

@Repository
public interface PostRepo extends CrudRepository<Post, Long> {
    Post getPostByPostName(String name);
    String deleteByPostName(String name);
}
