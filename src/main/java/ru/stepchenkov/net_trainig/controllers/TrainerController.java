package ru.stepchenkov.net_trainig.controllers;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.dataset.DataSet;
import ru.stepchenkov.net_trainig.service.MetricsReaderImpl;
import ru.stepchenkov.net_trainig.service.TrainingService;
import ru.stepchenkov.net_trainig.utils.DataReader;

public class TrainerController {
    private final TrainingService training;
    private final DataReader dataReader;

    // Конструктор принимает объекты для тренировки модели и чтения данных
    public TrainerController(TrainingService training, DataReader dataReader) {
        this.training = training;
        this.dataReader = dataReader;
    }

    // Метод для тренировки модели
    public void trainModel(int numFeatures) {
        // Получение объекта нейронной сети для обучения соответствующей модели
        MultiLayerNetwork model = training.getModel();
        // Получение обучающих данных из файла
        DataSet trainData = dataReader.getTrainData(numFeatures, new MetricsReaderImpl());
        // Получение тестовых данных из файла
        DataSet testData = dataReader.getTestData(numFeatures, new MetricsReaderImpl());

        // Обучение модели на обучающих данных
        model.fit(trainData);

        // Вычисление метрик на тестовых данных и вывод результатов обучения модели
        training.evaluate(model, testData);
    }
}