package ru.stepchenkov.rest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.stepchenkov.rest.entity.Department;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentModel {
    private int department;

    public static DepartmentModel toModel(Department dep) {
        DepartmentModel model = new DepartmentModel();
        model.setDepartment(dep.getDep());
        return model;
    }
}
