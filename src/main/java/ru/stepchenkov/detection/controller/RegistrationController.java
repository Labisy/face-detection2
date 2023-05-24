package ru.stepchenkov.detection.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.stepchenkov.detection.model.RegistrationModel;
import ru.stepchenkov.detection.service.RegistrationService;

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
