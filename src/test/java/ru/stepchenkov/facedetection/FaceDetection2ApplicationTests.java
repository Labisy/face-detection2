package ru.stepchenkov.facedetection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.stepchenkov.rest.DetectionApplication;

@SpringBootTest(classes = DetectionApplication.class)
class FaceDetection2ApplicationTests {

    @Test
    void contextLoads() {
        Assertions.assertEquals("1", "1");
    }

}
