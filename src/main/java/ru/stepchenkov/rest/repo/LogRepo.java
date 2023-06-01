package ru.stepchenkov.rest.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.stepchenkov.rest.entity.Log;
@Repository
public interface LogRepo extends CrudRepository<Log, Long> {
}
