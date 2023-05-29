package ru.stepchenkov.net_trainig.utils;

import org.nd4j.linalg.api.ndarray.INDArray;

import java.io.File;
import java.util.List;

public interface MetricsReader {
    // Метод для получения числовых метрик из файла и преобразования их в формат INDArray
    INDArray getNumMetrics(String path, int numFeatures);

    // Метод для получения меток (Label) из файла и преобразования их в формат INDArray
    INDArray getINDLabel(String path);

    // Метод для получения списка всех строк из файла
    List<String> getAllLine(File file);

    // Метод для получения меток (Label) из файла и преобразования их в массив строк
    String[] getLabelFomFile(File file);
}