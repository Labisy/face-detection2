package ru.stepchenkov.detection.repo;

import org.springframework.data.repository.CrudRepository;
import ru.stepchenkov.detection.entity.Log;

public interface LogRepo extends CrudRepository<Log, Long> {
}
