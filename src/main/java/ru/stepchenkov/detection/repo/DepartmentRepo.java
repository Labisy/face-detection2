package ru.stepchenkov.detection.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.stepchenkov.detection.entity.Department;

@Repository
public interface DepartmentRepo extends CrudRepository<Department, Long> {
    Department findByDep(int value);
}
