package ru.stepchenkov.facedetection.face.comporator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import ru.stepchenkov.face.comporator.FaceComparator;

import java.io.File;
import java.io.IOException;

class FaceComparatorTest {
    private FaceComparator comparator;

    @BeforeEach
    void init() throws IOException {
        comparator = new FaceComparator();
    }

    @Test
    @DisplayName("uploading faces and checking for similarity")
    void compareFaceTest() throws IOException {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat image1 = Imgcodecs.imread("src/main/resources/templates/m1.jpg");
        Mat image2 = Imgcodecs.imread("src/main/resources/templates/m2.jpg");
        boolean result = comparator.compareFaces(image1, image2);

        Assertions.assertThat(result).isTrue();
    }
}
