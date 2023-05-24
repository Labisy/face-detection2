package ru.stepchenkov.detection.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.stepchenkov.detection.entity.Info;

@Repository
public interface InfoRepo extends CrudRepository<Info, Long> {
}
