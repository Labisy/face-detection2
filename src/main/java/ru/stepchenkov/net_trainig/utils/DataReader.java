package ru.stepchenkov.net_trainig.utils;

import org.nd4j.linalg.dataset.DataSet;

public interface DataReader {
    // Метод для получения обучающих данных из файла и преобразования их в формат DataSet
    DataSet getTrainData(int numFeatures, MetricsReader metricsReader);

    // Метод для получения тестовых данных из файла и преобразования их в формат DataSet
    DataSet getTestData(int numFeatures, MetricsReader metricsReader);
}