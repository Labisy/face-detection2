package ru.stepchenkov.face.comporator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import ru.stepchenkov.face.controller.FaceDetector;
import ru.stepchenkov.face.converter.ImageConverter;
import ru.stepchenkov.face.service.ModelLoader;

import java.io.IOException;

// Класс для сравнения двух лиц на фотографиях
public class FaceComparator {
    private final double THRESHOLD = 0.5;
    private final MultiLayerNetwork model;
    private final Logger LOGGER = Logger.getLogger(FaceComparator.class);

    public FaceComparator() throws IOException {
        // Загрузка натренированной модели
        ModelLoader loader = new ModelLoader();
        model = loader.loadModel();
        if (model == null) {
            LOGGER.log(Level.ERROR, "Не удалось распознать модель");
            throw new IOException("Модель не была загружена");
        }
    }

    // Метод для сравнения двух лиц на фотографиях
    public boolean compareFaces(Mat image1, Mat image2) throws IOException {
        // Обрезаем лица на фотографиях
        FaceDetector detector = new FaceDetector();
        Mat cropFace1 = detector.cropFace(image1);
        Mat cropFace2 = detector.cropFace(image2);

        // Конвертируем изображения в градации серого
        Mat firstFace = new Mat();
        Mat secondFace = new Mat();
        Imgproc.cvtColor(cropFace1, firstFace, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(cropFace2, secondFace, Imgproc.COLOR_BGR2GRAY);

        // Проверяем, удалось ли конвертировать изображения
        if (firstFace.empty() || secondFace.empty()) {
            LOGGER.log(Level.ERROR, "Не смог загрузить фото с картинки");
            return false;
        }

        // Улучшение контрастности на изображении
        Imgproc.equalizeHist(firstFace, firstFace);
        Imgproc.equalizeHist(secondFace, secondFace);

        // Преобразование изображений в массивы INDArray
        ImageConverter converter = new ImageConverter();
        INDArray input1 = converter.imageToINDArray(firstFace);
        INDArray input2 = converter.imageToINDArray(secondFace);

        // Используем модель для прогнозирования встраивания граней во входные данные
        INDArray embed1 = model.output(input1);
        INDArray embed2 = model.output(input2);

        // Вычисление косинусного сходства между вложениями
        double similarity = cosineSimilarity(embed1, embed2);

        return similarity > THRESHOLD;
    }

    // Метод для вычисления косинусного сходства между двумя вложениями
    private double cosineSimilarity(INDArray a, INDArray b) {
        INDArray aTranspose = a.transpose();
        INDArray dotProduct = a.mmul(aTranspose); // Замена функции a.dot(b.transpose())
        double dotProductValue = dotProduct.getDouble(0);

        double normA = a.norm2().getDouble(0);
        double normB = b.norm2().getDouble(0);

        return dotProductValue / (normA * normB);
    }
}