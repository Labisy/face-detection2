package ru.stepchenkov.face;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import ru.stepchenkov.face.comporator.FaceComparator;

import java.io.IOException;

public class StartComparing {

    public boolean startComparing(String path1, String path2) {
        // Загрузка фотографий для сравнения
        Mat image1 = Imgcodecs.imread(path1);
        Mat image2 = Imgcodecs.imread(path2);

        // Создание объекта класса FaceComparator и сравнение лиц
        FaceComparator comparator = null;
        boolean result = false;
        try {
            comparator = new FaceComparator();
            result = comparator.compareFaces(image1, image2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
