package ru.stepchenkov.face.controller;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import ru.stepchenkov.face.type.HaarcascadeType;

// Класс для обнаружения лиц на изображении
public class FaceDetector {
    private final Logger LOGGER = Logger.getLogger(FaceDetector.class);
    private final CascadeClassifier faceDetector;

    public FaceDetector() {
        // Загрузка классификатора каскадов
        faceDetector = new CascadeClassifier(HaarcascadeType.FRONTALFACE.get());
    }

    // Метод для обрезания изображения и установления стандартного размера
    public Mat cropFace(Mat image) {
        try {
            MatOfRect faceDetections = new MatOfRect();

            // Обнаружение лиц на изображении
            faceDetector.detectMultiScale(image, faceDetections);

            // Первое обнаруженное лицо
            Rect[] facesArray = faceDetections.toArray();
            if (facesArray.length > 0) {
                Rect faceRect = facesArray[0];

                // Обрезаем изображение до прямоугольника грани
                Mat croppedImage = new Mat(image, faceRect);

                // Изменяем размер обрезанного изображения до стандартного размера
                Mat resizedImage = new Mat();
                Size size = new Size(256, 256);
                Imgproc.resize(croppedImage, resizedImage, size);

                return resizedImage;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Не удалось вырезать лицо: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return image;
    }
}