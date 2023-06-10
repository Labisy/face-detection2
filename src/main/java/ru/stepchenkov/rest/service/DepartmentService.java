package ru.stepchenkov.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stepchenkov.rest.entity.Department;
import ru.stepchenkov.rest.exception.DepartmentAlreadyExistsException;
import ru.stepchenkov.rest.exception.DepartmentNotFoundException;
import ru.stepchenkov.rest.model.DepartmentModel;
import ru.stepchenkov.rest.repo.DepartmentRepo;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepo departmentRepo;

    public Department save(Department department) throws DepartmentAlreadyExistsException {
        if (departmentRepo.findByDep(department.getDep()) != null)
            throw new DepartmentAlreadyExistsException("Такой отдел уже создан");
        return departmentRepo.save(department);
    }

    public int deleteDepartment(int dep) throws DepartmentNotFoundException {
        if (departmentRepo.findByDep(dep) == null)
            throw new DepartmentNotFoundException("Такого пользователя не существует");
        departmentRepo.deleteDepartmentByDep(dep);
        return dep;
    }

    public List<DepartmentModel> getAll() {
        List<Department> departments = (List<Department>) departmentRepo.findAll();
        return departments.stream().map(DepartmentModel::toModel).toList();
    }
}
