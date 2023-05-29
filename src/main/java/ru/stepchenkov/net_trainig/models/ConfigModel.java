package ru.stepchenkov.net_trainig.models;

import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.SubsamplingLayer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;

public class ConfigModel {
    private final MultiLayerConfiguration config;

    // Конструктор класса ConfigModel, в котором задаются параметры и конфигурация для многослойного перцептрона
    public ConfigModel() {
        // Создание конфигурации для многослойного перцептрона с заданными параметрами
        this.config = new NeuralNetConfiguration.Builder()
                .seed(123)
                .updater(new Adam())
                .list()
                .layer(0, new ConvolutionLayer.Builder()
                        .nIn(3)
                        .nOut(16)
                        .kernelSize(5, 5)
                        .stride(1, 1)
                        .activation(Activation.RELU)
                        .build())
                .layer(1, new SubsamplingLayer.Builder()
                        .kernelSize(2, 2)
                        .stride(2, 2)
                        .build())
                .layer(2, new ConvolutionLayer.Builder()
                        .nIn(16)
                        .nOut(20)
                        .kernelSize(5, 5)
                        .stride(1, 1)
                        .activation(Activation.RELU)
                        .build())
                .layer(3, new SubsamplingLayer.Builder()
                        .kernelSize(2, 2)
                        .stride(2, 2)
                        .build())
                .layer(4, new DenseLayer.Builder()
                        .nOut(500)
                        .activation(Activation.RELU)
                        .build())
                .layer(5, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nOut(2)
                        .activation(Activation.SOFTMAX)
                        .build())
                // Определение типа входных данных, в данном случае - сверточные изображения размером 32x32 с 3 каналами
                .setInputType(InputType.convolutional(32, 32, 3))
                .build();
    }

    // Получение конфигурации многослойного перцептрона
    public MultiLayerConfiguration getConfig() {
        return config;
    }
}