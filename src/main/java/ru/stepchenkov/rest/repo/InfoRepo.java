package ru.stepchenkov.rest.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.stepchenkov.rest.entity.Info;

@Repository
public interface InfoRepo extends CrudRepository<Info, Long> {
}
