package ru.stepchenkov.face;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import ru.stepchenkov.face.comporator.FaceComparator;

import java.io.IOException;

public class StartComparing {

    public void compareFace(String path1, String path2) {
        // Загрузка фотографий для сравнения
        Mat image1 = Imgcodecs.imread("path/to/image1.jpg");
        Mat image2 = Imgcodecs.imread("path/to/image2.jpg");

        // Создание объекта класса FaceComparator и сравнение лиц
        FaceComparator comparator = null;
        boolean result = false;
        try {
            comparator = new FaceComparator();
            result = comparator.compareFaces(image1, image2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (result) {
            System.out.println("Лица на фотографиях совпадают");
        } else {
            System.out.println("Лица на фотографиях не совпадают");
        }
    }
}
