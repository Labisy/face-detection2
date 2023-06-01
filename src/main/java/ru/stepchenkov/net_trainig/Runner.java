package ru.stepchenkov.net_trainig;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.stepchenkov.net_trainig.service.TrainingService;
import ru.stepchenkov.net_trainig.service.DataReaderImpl;
import ru.stepchenkov.net_trainig.controllers.TrainerController;

public class Runner {
    public static void runTraining() {
        TrainerController modelTrainer = new TrainerController(new TrainingService(), new DataReaderImpl());
        modelTrainer.trainModel(4);
    }

}