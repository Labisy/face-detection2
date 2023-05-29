package ru.stepchenkov.rest.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.stepchenkov.rest.entity.Time;

@Repository
public interface TimeRepo extends CrudRepository<Time, Long> {
}
