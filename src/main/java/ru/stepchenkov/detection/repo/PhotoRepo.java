package ru.stepchenkov.detection.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.stepchenkov.detection.entity.Photo;

@Repository
public interface PhotoRepo extends CrudRepository<Photo, Long> {
}
