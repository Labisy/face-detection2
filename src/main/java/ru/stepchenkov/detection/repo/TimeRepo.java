package ru.stepchenkov.detection.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.stepchenkov.detection.entity.Time;

@Repository
public interface TimeRepo extends CrudRepository<Time, Long> {
}
