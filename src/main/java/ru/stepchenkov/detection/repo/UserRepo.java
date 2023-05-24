package ru.stepchenkov.detection.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.stepchenkov.detection.entity.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    User findByServiceNumber(Integer number);
}
