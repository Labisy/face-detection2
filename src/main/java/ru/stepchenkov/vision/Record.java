package ru.stepchenkov.vision;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
import ru.stepchenkov.vision.type.HaarcascadeType;

public class Record {
    public static void main(String[] args) {
        record();
    }
    public static void record() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // загружаем классификатор лиц
        CascadeClassifier faceDetector = new CascadeClassifier(HaarcascadeType.FRONTALFACE_ALT.get());

        // инициализируем видеопоток и создаем объект для записи нового видео
        VideoCapture videoCapture = new VideoCapture("src/main/resources/static/input.mp4");
        int width = (int) videoCapture.get(3);
        int height = (int) videoCapture.get(4);
        VideoWriter videoWriter = new VideoWriter("src/main/resources/static/output.mp4", VideoWriter.fourcc('X', 'V', 'I', 'D'), videoCapture.get(5),
                new org.opencv.core.Size(width, height), true);

        // обрабатываем каждый кадр видео
        while (videoCapture.grab()) {
            Mat frame = new Mat();
            videoCapture.retrieve(frame);

            // находим лица на кадре
            MatOfRect faceDetections = new MatOfRect();
            faceDetector.detectMultiScale(frame, faceDetections);

            // рисуем прямоугольник вокруг каждого лица
            for (Rect rect : faceDetections.toArray()) {
                Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                        new Scalar(0, 0, 255), 3);
            }

            // записываем новый кадр в файл
            videoWriter.write(frame);
        }

        // освобождаем ресурсы
        videoCapture.release();
        videoWriter.release();
    }
}
