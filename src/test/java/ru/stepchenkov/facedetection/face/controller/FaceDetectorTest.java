package ru.stepchenkov.facedetection.face.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import ru.stepchenkov.face.controller.FaceDetector;

class FaceDetectorTest {
   // private FaceDetector faceDetector;

    @BeforeEach
    void init() {
        //faceDetector = new FaceDetector();
    }

    @Test
    @DisplayName("crop image and resize")
    void cropFace() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//
//        // загрузка данных в матрицы mat1 и mat2
//        Mat mat1 = Imgcodecs.imread("src/main/resources/templates/m1.jpg");
//        Mat mat2 = Imgcodecs.imread("src/main/resources/templates/croppedM1.jpg");
//
//        Mat result = new Mat();
//        // загрузка в result результат сравнения
//        Core.compare(mat1, mat2, result, Core.CMP_EQ);
//
//        Assertions.assertThat(result).isNotNull();
//        // проверка, что все элементы матрицы result равны 1 (то есть матрицы mat1 и mat2 равны)
//        Assertions.assertThat(Core.minMaxLoc(result).maxVal).isEqualTo(1);
    }
}
