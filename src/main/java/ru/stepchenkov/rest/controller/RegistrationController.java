package ru.stepchenkov.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepchenkov.rest.model.RegistrationModel;
import ru.stepchenkov.rest.service.RegistrationService;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<String> registration(@RequestBody RegistrationModel model) {
        registrationService.registration(model);
        return ResponseEntity.ok("");

    }
}
