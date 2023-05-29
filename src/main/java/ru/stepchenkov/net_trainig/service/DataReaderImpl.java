package ru.stepchenkov.net_trainig.service;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import ru.stepchenkov.net_trainig.utils.DataReader;
import ru.stepchenkov.net_trainig.utils.MetricsReader;
import ru.stepchenkov.net_trainig.type.PathType;

public class DataReaderImpl implements DataReader {
    // Получение данных и меток обучающей выборки из файлов, используя объект MetricsReader
    @Override
    public DataSet getTrainData(int numFeatures, MetricsReader metricsReader) {
        // Получение массива признаков обучающей выборки
        INDArray trainFeatures = metricsReader.getNumMetrics(PathType.TRAIN_FEATURES.get(), numFeatures);
        // Получение массива меток обучающей выборки
        INDArray trainLabels = metricsReader.getINDLabel(PathType.TRAIN_FEATURES.get());

        // Создание объекта DataSet, содержащего массив признаков и соответствующие им метки обучающей выборки
        return new DataSet(trainFeatures, trainLabels);
    }

    // Получение данных и меток тестовой выборки из файлов, используя объект MetricsReader
    @Override
    public DataSet getTestData(int numFeatures, MetricsReader metricsReader) {
        // Получение массива признаков тестовой выборки
        INDArray testFeatures = metricsReader.getNumMetrics(PathType.TEST_FEATURES.get(), numFeatures);
        // Получение массива меток обучающей выборки
        INDArray testLabels = metricsReader.getINDLabel(PathType.TRAIN_FEATURES.get());

        // Создание объекта DataSet, содержащего массив признаков и соответствующие им метки тестовой выборки
        return new DataSet(testFeatures, testLabels);
    }
}