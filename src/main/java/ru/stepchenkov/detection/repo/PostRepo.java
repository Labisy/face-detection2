package ru.stepchenkov.detection.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.stepchenkov.detection.entity.Post;

@Repository
public interface PostRepo extends CrudRepository<Post, Long> {
    Post getPostByPostName(String name);
}
