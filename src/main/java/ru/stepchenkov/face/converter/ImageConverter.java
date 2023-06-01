package ru.stepchenkov.face.converter;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

// Класс для преобразования Mat в массив INDArray
public class ImageConverter {
    // Метод для преобразования Mat в 4D-матрицу в формате (пакет, каналы, высота, ширина)
    public INDArray imageToINDArray(Mat image) {
        // Изменение размера изображения до 96х96 пикселей
        Mat resized = new Mat();
        Imgproc.resize(image, resized, new Size(96, 96));
        // Приведение значений пикселей к диапазону от 0 до 1
        resized.convertTo(resized, CvType.CV_32F, 1.0 / 255, 0);

        // Создание 4D-массива из изображения
        INDArray array = Nd4j.create(resized.rows(), resized.cols(), 1, 1);
        for (int i = 0; i < resized.rows(); i++) {
            for (int j = 0; j < resized.cols(); j++) {
                // Заполнение массива значениями яркости пикселей изображения
                array.putScalar(new int[]{i, j, 0, 0}, (float) resized.get(i, j)[0]);
            }
        }

        return array;
    }
}