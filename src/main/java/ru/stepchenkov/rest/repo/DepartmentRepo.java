package ru.stepchenkov.rest.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.stepchenkov.rest.entity.Department;

@Repository
public interface DepartmentRepo extends CrudRepository<Department, Long> {
    Department findByDep(int value);
    int deleteDepartmentByDep(int value);
}
