package ru.stepchenkov.rest.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.stepchenkov.rest.entity.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    User findByServiceNumber(Integer number);
}
