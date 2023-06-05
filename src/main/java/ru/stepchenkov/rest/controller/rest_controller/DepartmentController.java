package ru.stepchenkov.rest.controller.rest_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepchenkov.rest.entity.Department;
import ru.stepchenkov.rest.exception.DepartmentAlreadyException;
import ru.stepchenkov.rest.exception.DepartmentNotFoundException;
import ru.stepchenkov.rest.service.DepartmentService;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity saveDepartment(@RequestBody Department department) {
        try {
            return new ResponseEntity<>(departmentService.save(department), HttpStatus.OK);
        } catch (DepartmentAlreadyException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{value}")
    public ResponseEntity deleteDepartment(@PathVariable int value) {
        try {
            departmentService.deleteDepartment(value);
            return ResponseEntity.ok().build();
        } catch (DepartmentNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllDepartment() {
        return new ResponseEntity(departmentService.getAll(), HttpStatus.OK);
    }
}
