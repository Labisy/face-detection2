package ru.stepchenkov.net_trainig.service;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import ru.stepchenkov.net_trainig.models.ConfigModel;

public class TrainingService {
    private final Logger LOGGER = Logger.getLogger(TrainingService.class);

    //создает новую модель многослойного перцептрона с заданным количеством входных признаков и заданными параметрами.
    public MultiLayerNetwork getModel() {
        ConfigModel config = new ConfigModel();
        MultiLayerNetwork model = new MultiLayerNetwork(config.getConfig());
        model.init();

        return model;
    }

    //оценивает производительность модели на тестовых данных и выводит результат на экран
    public void evaluate(MultiLayerNetwork model, DataSet testData) {
        Evaluation eval = new Evaluation(2);
        INDArray output = model.output(testData.getFeatures());
        eval.eval(testData.getLabels(), output);
        LOGGER.log(Level.DEBUG, "оценка производительности нейросети " + eval.stats());
    }
}
