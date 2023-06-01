package ru.stepchenkov.face.service;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import ru.stepchenkov.face.type.FileType;

import java.io.File;
import java.io.IOException;

// Класс для загрузки модели нейронной сети
public class ModelLoader {
    private static final Logger LOGGER = Logger.getLogger(ModelLoader.class);

    public MultiLayerNetwork loadModel() throws IOException {
        // Получение файла модели
        File modelFile = new File(FileType.NET_MODEL.get());
        // Проверка существования файла модели
        if (!modelFile.exists()) {
            LOGGER.log(Level.ERROR, "Ошибка в получении натренированной модели");
            return null;
        }
        // Восстановление многослойной нейронной сети из файла
        return ModelSerializer.restoreMultiLayerNetwork(modelFile);
    }
}