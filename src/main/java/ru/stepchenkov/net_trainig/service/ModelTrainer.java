package ru.stepchenkov.net_trainig.service;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.dataset.DataSet;
import ru.stepchenkov.net_trainig.controllers.Training;
import ru.stepchenkov.net_trainig.utils.DataReader;

public class ModelTrainer {
    private final Training training;
    private final DataReader dataReader;

    // Конструктор принимает объекты для тренировки модели и чтения данных
    public ModelTrainer(Training training, DataReader dataReader) {
        this.training = training;
        this.dataReader = dataReader;
    }

    // Метод для тренировки модели
    public void trainModel(int numFeatures) {
        // Получение объекта нейронной сети для обучения соответствующей модели
        MultiLayerNetwork model = training.getModel(numFeatures);
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