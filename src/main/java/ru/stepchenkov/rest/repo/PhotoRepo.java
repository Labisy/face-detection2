package ru.stepchenkov.rest.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.stepchenkov.rest.entity.Photo;

@Repository
public interface PhotoRepo extends CrudRepository<Photo, Long> {
}
