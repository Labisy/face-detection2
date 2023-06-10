package ru.stepchenkov.facedetection.rest.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ru.stepchenkov.rest.entity.Department;
import ru.stepchenkov.rest.exception.DepartmentAlreadyExistsException;
import ru.stepchenkov.rest.exception.DepartmentNotFoundException;
import ru.stepchenkov.rest.repo.DepartmentRepo;
import ru.stepchenkov.rest.service.DepartmentService;

class DepartmentServiceTest {
    @InjectMocks
    private DepartmentService departmentService;
    @Mock
    private DepartmentRepo departmentRepo;
    private Department dep;

    public DepartmentServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    public void init() {
        dep = new Department();
        dep.setDep(143);
    }

    @Test
    @DisplayName("save department into database")
    void saveTest() throws DepartmentAlreadyExistsException {

        Mockito.when(departmentRepo.save(ArgumentMatchers.any())).thenReturn(dep);
        Department response = departmentService.save(dep);

        Assertions.assertThat(response)
                .isNotNull()
                .extracting(Department::getDep)
                .isEqualTo(143);
    }

    @Test
    @DisplayName("delete department from database")
    void deleteTest() throws DepartmentNotFoundException {

        Mockito.when(departmentRepo.deleteDepartmentByDep(143)).thenReturn(143);
        Mockito.when(departmentRepo.findByDep(143)).thenReturn(dep);
        int response = departmentService.deleteDepartment(143);

        Assertions.assertThat(response).isEqualTo(143);
    }
}
