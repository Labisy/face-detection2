package ru.stepchenkov.net_trainig.service;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import ru.stepchenkov.net_trainig.utils.MetricsReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MetricsReaderImpl implements MetricsReader {

    // Получение массива данных из файла по указанному пути и заданному количеству признаков
    @Override
    public INDArray getNumMetrics(String path, int numFeatures) {
        // Получение объекта файла по указанному пути
        File dataFile = new File(path);
        // Получение списка строк из файла
        List<String> arr = getAllLine(dataFile);

        // Создание объекта NDArray для хранения массива данных
        INDArray trainDataArray = Nd4j.create(arr.size(), numFeatures);

        // Проход по каждой строке данных и заполнение соответствующей строки объекта NDArray значениями признаков
        for (int i = 0; i < arr.size(); i++) {
            String[] line = arr.get(i).split(" ");

            for (int j = 1; j < numFeatures; j++) {
                trainDataArray.putScalar(i, j, Double.parseDouble(line[j]));
            }
        }

        // Возвращение объекта NDArray с данными
        return trainDataArray;
    }

    // Получение меток классов из файла по указанному пути
    @Override
    public INDArray getINDLabel(String path) {
        // Получение объекта файла по указанному пути
        File allMetrixFile = new File(path);

        // Проверка, что указанный путь не является директорией
        if (allMetrixFile.isDirectory()) {
            throw new RuntimeException();
        }

        // Получение всех меток классов из файла
        String[] label = getLabelFomFile(allMetrixFile);
        // Преобразование массива меток классов в строку
        String[] stringLabels = Nd4j.create(label).toString().replaceAll("[\\[\\]]", "").split(",");
        int[] labelIndices = new int[label.length];

        // Получение индексов меток классов
        for (int i = 0; i < label.length; i++) {
            labelIndices[i] = Arrays.asList(stringLabels).indexOf(label[i]);
        }

        // Получение количества классов
        int numClasses = stringLabels.length;
        // Создание объекта NDArray для хранения меток классов
        INDArray trainLabels = Nd4j.zeros(label.length, numClasses);

        // Заполнение объекта NDArray значениями меток классов
        for (int i = 0; i < label.length; i++) {
            trainLabels.putScalar(i, labelIndices[i], 1.0);
        }

        // Возвращение объекта NDArray с метками классов
        return trainLabels;
    }

    // Получение списка строк из файла
    @Override
    public List<String> getAllLine(File file) {
        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Получение меток классов из файла в формате LIBSVM
    @Override
    public String[] getLabelFomFile(File file) {
        // Получение всех строк данных из файла
        ArrayList<String> allLine = (ArrayList<String>) getAllLine(file);

        // Создание массива для хранения меток классов
        String[] label = new String[allLine.size()];

        // Проход по каждой строке данных и извлечение метки класса
        for (int i = 0; i < allLine.size(); i++) {
            String[] line = allLine.get(i).split(" ");
            label[i] = line[0];
        }

        // Возвращение массива меток классов
        return label;
    }
}
