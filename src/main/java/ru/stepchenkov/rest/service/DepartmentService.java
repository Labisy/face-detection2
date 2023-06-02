package ru.stepchenkov.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stepchenkov.rest.entity.Department;
import ru.stepchenkov.rest.exception.DepartmentAlreadyException;
import ru.stepchenkov.rest.exception.DepartmentNotFoundException;
import ru.stepchenkov.rest.repo.DepartmentRepo;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepo departmentRepo;

    public Department save(Department department) throws DepartmentAlreadyException {
        if (departmentRepo.findByDep(department.getDep()) != null)
            throw new DepartmentAlreadyException("Такой пользователь уже создан");
        return departmentRepo.save(department);
    }

    public Integer deleteDepartment(int dep) throws DepartmentNotFoundException {
        if (departmentRepo.findByDep(dep) == null)
            throw new DepartmentNotFoundException("Такого пользователя не существует");
        departmentRepo.deleteDepartmentByDep(dep);
        return dep;
    }
}
