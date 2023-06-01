package ru.stepchenkov.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.stepchenkov.net_trainig.Runner;

@RestController
@RequestMapping("/training")
public class TrainController {

    @GetMapping("/train")
    public ResponseEntity trainModel() {
        Runner.runTraining();
        return ResponseEntity.ok("модель успешно обучена");
    }
}
