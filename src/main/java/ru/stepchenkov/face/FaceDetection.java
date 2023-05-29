package ru.stepchenkov.face;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import ru.stepchenkov.face.type.FileType;
import ru.stepchenkov.face.type.HaarcascadeType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FaceDetection {
    // Загрузка библиотеки OpenCV
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    // Пороговое значение для сравнения лиц
    private final Logger LOGGER = Logger.getLogger(FaceDetection.class);
    private final double THRESHOLD = 0.5;

    // Метод для обнаружения лиц на изображении
    public Mat detectFaces(Mat image) {
        // Загрузка классификатора каскадов
        CascadeClassifier faceDetector = new CascadeClassifier(HaarcascadeType.FRONTALFACE.get());

        // Обнаружение лиц на изображении
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        // Вырезание лиц и составление списка
        List<Mat> faces = new ArrayList<>();
        for (int i = 0; i < faceDetections.toArray().length; i++) {
            Rect rect = faceDetections.toArray()[i];
            faces.add(new Mat(image, rect));
        }

        // Соединение всех лиц в один Mat
        Mat result = new Mat();
        Core.hconcat(faces, result);

        return result;
    }

    // Метод для сравнения двух лиц на фотографиях
    public boolean compareFaces(Mat image1, Mat image2) throws IOException {
        // Обрезаем лица на фотографиях
        Mat cropFace1 = cropFace(image1);
        Mat cropFace2 = cropFace(image2);

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
        INDArray input1 = imageToINDArray(firstFace);
        INDArray input2 = imageToINDArray(secondFace);

        // Загрузка натренированной модели
        MultiLayerNetwork model = loadModel();
        if (model == null) {
            LOGGER.log(Level.ERROR, "Не удалось распознать модель");
            return false;
        }

        // Используем модель для прогнозирования встраивания граней во входные данные
        INDArray embed1 = model.output(input1);
        INDArray embed2 = model.output(input2);

        // Вычисление косинусного сходства между вложениями
        double similarity = cosineSimilarity(embed1, embed2);

        return similarity > THRESHOLD;
    }

    // Метод для обрезания изображения и установления стандартного размера
    public Mat cropFace(Mat image) {
        try {
            // Создаем детектор лиц из файла cascade
            CascadeClassifier faceDetector = new CascadeClassifier(HaarcascadeType.FRONTALFACE.get());
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

    // Метод для загрузки натренированной модели
    private MultiLayerNetwork loadModel() throws IOException {
        File modelFile = new File(FileType.NET_MODEL.get());
        if (!modelFile.exists()) {
            LOGGER.log(Level.ERROR, "ошибка в получении натренированной модели");
            return null;
        }
        return ModelSerializer.restoreMultiLayerNetwork(modelFile);
    }

    // Метод для преобразования Mat в 4D-матрицу в формате (пакет, каналы, высота, ширина)
    private INDArray imageToINDArray(Mat image) {
        Mat resized = new Mat();
        Imgproc.resize(image, resized, new Size(96, 96));
        resized.convertTo(resized, CvType.CV_32F, 1.0 / 255, 0);

        INDArray array = Nd4j.create(resized.rows(), resized.cols(), 1, 1);
        for (int i = 0; i < resized.rows(); i++) {
            for (int j = 0; j < resized.cols(); j++) {
                array.putScalar(new int[]{i, j, 0, 0}, (float) resized.get(i, j)[0]);
            }
        }

        return array;
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

    private void test() throws IOException {
        FaceDetection detection = new FaceDetection();
        detection.detectFaces(new Mat());
        compareFaces(new Mat(), new Mat());
    }
}
