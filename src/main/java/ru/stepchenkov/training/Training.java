package ru.stepchenkov.training;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import ru.stepchenkov.training.model.ConfigModel;
import ru.stepchenkov.training.type.PathType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Training {
    private final Logger LOGGER = Logger.getLogger(Training.class);

    public void trainModel(int numFeatures) {
        ConfigModel config = new ConfigModel();
        MultiLayerNetwork model = new MultiLayerNetwork(config.getConfig());
        model.init();

        //Создание массива функций и меток для trainData
        INDArray trainFeatures = Nd4j.create(getNumMetrics(PathType.TRAIN_FEATURES.get(), numFeatures).toFloatVector());
        INDArray trainLabels = Nd4j.create(getINDLabel(PathType.TRAIN_FEATURES.get()).toFloatVector());

        //Создание массива функций и меток для testData
        INDArray testFeatures = Nd4j.create(getNumMetrics(PathType.TEST_FEATURES.get(), numFeatures).toFloatVector());
        INDArray testLabels = Nd4j.create(getINDLabel(PathType.TRAIN_FEATURES.get()).toFloatVector());

        // Создание экземпляров класса DataSet
        DataSet trainData = new DataSet(trainFeatures, trainLabels);
        DataSet testData = new DataSet(testFeatures, testLabels);

        //Тренировка модели
        model.fit(trainData);

        //Оценка производительности
        Evaluation eval = new Evaluation(2);
        INDArray output = model.output(testData.getFeatures());
        eval.eval(testData.getLabels(), output);
        LOGGER.log(Level.DEBUG, "оценка производительности нейросети " + eval.stats());
    }


    private INDArray getNumMetrics(String path, int numFeatures) {
        // Указываем путь к файлу с данными
        File dataFile = new File(path);
        List<String> arr = getAllLine(dataFile);

        INDArray trainDataArray = Nd4j.create(arr.size(), numFeatures);
        for (int i = 0; i < arr.size(); i++) {
            String[] line = arr.get(i).split(" ");
            for (int j = 1; j < numFeatures; j++) {
                trainDataArray.putScalar(i, j, Double.parseDouble(line[j]));
            }
        }

        return trainDataArray;
    }

    private INDArray getINDLabel(String path) {
        File allMetrixFile = new File(path);
        if (allMetrixFile.isDirectory()) throw new RuntimeException();

        String[] label = getLabelFomFile(allMetrixFile);

         // Массив текстовых меток
        String[] stringLabels = Nd4j.create(label).toString().replaceAll("[\\[\\]]", "").split(","); // Создание массива уникальных меток
        int[] labelIndices = new int[label.length];
        for (int i = 0; i < label.length; i++) {
            labelIndices[i] = Arrays.asList(stringLabels).indexOf(label[i]); // Получение индексов меток из массива строковых меток
        }
        int numClasses = stringLabels.length; // Количество уникальных меток
        INDArray trainLabels = Nd4j.zeros(label.length, numClasses); // Создание двумерного массива для хранения one-hot векторов
        for (int i = 0; i < label.length; i++) {
            trainLabels.putScalar(i, labelIndices[i], 1.0); // Кодирование меток в массив one-hot векторов
        }

        return trainLabels;
    }

    private List<String> getAllLine(File file) {
        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String[] getLabelFomFile(File file) {
        ArrayList<String> allLine = (ArrayList<String>) getAllLine(file);

        String[] label = new String[allLine.size()];
        for (int i = 0; i < allLine.size(); i++) {
            String[] line = allLine.get(i).split(" ");
            label[i] = line[0];
        }
        return label;
    }

}
