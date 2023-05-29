package ru.stepchenkov.net_trainig.controllers;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import ru.stepchenkov.net_trainig.models.ConfigModel;
import ru.stepchenkov.net_trainig.type.PathType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Training {
    private final Logger LOGGER = Logger.getLogger(Training.class);

    //тренирует модель на обучающих данных, используя заданное количество признаков, и оценивает ее производительность на тестовых данных.
    public void trainModel(int numFeatures) {
        MultiLayerNetwork model = getModel(numFeatures);
        DataSet trainData = getTrainData(numFeatures);
        DataSet testData = getTestData(numFeatures);
        model.fit(trainData);
        evaluate(model, testData);
    }

    //создает новую модель многослойного перцептрона с заданным количеством входных признаков и заданными параметрами.
    public MultiLayerNetwork getModel(int numFeatures) {
        ConfigModel config = new ConfigModel();
        MultiLayerNetwork model = new MultiLayerNetwork(config.getConfig());
        model.init();

        return model;
    }

    //загружает обучающие данные с заданным количеством признаков и создает объект DataSet из входных особенностей и соответствующих меток.
    private DataSet getTrainData(int numFeatures) {
        INDArray trainFeatures = getNumMetrics(PathType.TRAIN_FEATURES.get(), numFeatures);
        INDArray trainLabels = getINDLabel(PathType.TRAIN_FEATURES.get());

        return new DataSet(trainFeatures, trainLabels);
    }

    //загружает тестовые данные с заданным количеством признаков и создает объект DataSet из входных особенностей и соответствующих меток.
    private DataSet getTestData(int numFeatures) {
        INDArray testFeatures = getNumMetrics(PathType.TEST_FEATURES.get(), numFeatures);
        INDArray testLabels = getINDLabel(PathType.TRAIN_FEATURES.get());

        return new DataSet(testFeatures, testLabels);
    }

    //загружает числовые метрики из файла с заданным путем и заданным количеством признаков, и создает объект INDArray.
    private INDArray getNumMetrics(String path, int numFeatures) {
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

    //загружает метки из файла с заданным путем, создает объект INDArray для меток и возвращает его.
    private INDArray getINDLabel(String path) {
        File allMetrixFile = new File(path);
        if (allMetrixFile.isDirectory()) {
            throw new RuntimeException();
        }

        String[] label = getLabelFomFile(allMetrixFile);
        String[] stringLabels = Nd4j.create(label).toString().replaceAll("[\\[\\]]", "").split(",");
        int[] labelIndices = new int[label.length];

        for (int i = 0; i < label.length; i++) {
            labelIndices[i] = Arrays.asList(stringLabels).indexOf(label[i]);
        }

        int numClasses = stringLabels.length;
        INDArray trainLabels = Nd4j.zeros(label.length, numClasses);

        for (int i = 0; i < label.length; i++) {
            trainLabels.putScalar(i, labelIndices[i], 1.0);
        }

        return trainLabels;
    }

    //оценивает производительность модели на тестовых данных и выводит результат на экран
    public void evaluate(MultiLayerNetwork model, DataSet testData) {
        Evaluation eval = new Evaluation(2);
        INDArray output = model.output(testData.getFeatures());
        eval.eval(testData.getLabels(), output);
        LOGGER.log(Level.DEBUG, "оценка производительности нейросети " + eval.stats());
    }

    //возвращает список строк из текстового файла.
    private List<String> getAllLine(File file) {
        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //загружает метки из текстового файла и возвращает массив строк
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
