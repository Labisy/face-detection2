package ru.stepchenkov.facedetection.face.converter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import ru.stepchenkov.face.converter.ImageConverter;

class ImageConverterTest {
    private ImageConverter converter;

    @BeforeEach
    void init() {
        converter = new ImageConverter();
    }

    @Test
    @DisplayName("get arr fom mat")
    void getArrFromMat() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat img = Imgcodecs.imread("src/main/resources/templates/m1.jpg");

        // получаем 2 массива из одной и той-же фотографии
        INDArray array1 = converter.imageToINDArray(img);
        INDArray array2 = converter.imageToINDArray(img);
        // проверяем, что объект не null и при сравнении содержимого массивов они равны
        Assertions.assertThat(array1.toString()).isNotNull().isEqualTo(array2.toString());
    }
}
