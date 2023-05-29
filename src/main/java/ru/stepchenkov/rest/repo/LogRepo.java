package ru.stepchenkov.rest.repo;

import org.springframework.data.repository.CrudRepository;
import ru.stepchenkov.rest.entity.Log;

public interface LogRepo extends CrudRepository<Log, Long> {
}
