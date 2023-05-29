package ru.stepchenkov.net_trainig;

import ru.stepchenkov.net_trainig.controllers.Training;
import ru.stepchenkov.net_trainig.service.DataReaderImpl;
import ru.stepchenkov.net_trainig.service.ModelTrainer;

public class Runner {
    public static void main(String[] args) {
        ModelTrainer modelTrainer = new ModelTrainer(new Training(), new DataReaderImpl());
        modelTrainer.trainModel(4);
    }
}