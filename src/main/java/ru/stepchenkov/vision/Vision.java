package ru.stepchenkov.vision;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import ru.stepchenkov.vision.type.FileType;
import ru.stepchenkov.vision.type.HaarcascadeType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.opencv.features2d.AgastFeatureDetector.THRESHOLD;


public class Vision {
    private final Logger LOGGER = Logger.getLogger(Vision.class);

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Vision main = new Vision();
        main.findOnVideo();

    }

    public Mat detectFaces(Mat image) {
        // Загрузка классификатора каскадов
        CascadeClassifier faceDetector = new CascadeClassifier(HaarcascadeType.FRONTALFACE.get());

        // Обнаружение лиц на изображении
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        // Вырезание лиц и составление списка Mat
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

    public boolean compareFaces(Mat image1, Mat image2) throws IOException {
        // обрезаем лицо на фотографии
        Mat cropFace1 = cropFace(image1);
        Mat cropFace2 = cropFace(image2);

        // загрузка файла с лицами в серые цвета
        Mat firstFace = new Mat();
        Mat secondFace = new Mat();
        Imgproc.cvtColor(cropFace1, firstFace, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(cropFace2, secondFace, Imgproc.COLOR_BGR2GRAY);

        if (firstFace.empty() || secondFace.empty()) {
            LOGGER.log(Level.ERROR, "Не смог загрузить фото с картинки");
            return false;
        }

        Imgproc.equalizeHist(firstFace, firstFace);
        Imgproc.equalizeHist(secondFace, secondFace);
        // конвертируем картинку в массив
        INDArray input1 = imageToINDArray(firstFace);
        INDArray input2 = imageToINDArray(secondFace);

        // загрузка натренированной модели
        MultiLayerNetwork model = loadModel();
        LOGGER.log(Level.ERROR, "Не удалось распознать модель");

        // Используем модель для прогнозирования встраивания граней во входные данные
        INDArray embed1 = model.output(input1);
        INDArray embed2 = model.output(input2);
        // Вычислим косинусное сходство между вложениями
        double similarity = cosineSimilarity(embed1, embed2);
        return similarity > THRESHOLD;
    }

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
                Size size = new Size(256, 256); // Устанавливаем размер выходного изображения
                Imgproc.resize(croppedImage, resizedImage, size);

                return resizedImage;
            }
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "Не удалось вырезать лицо: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return image;
    }

    private MultiLayerNetwork loadModel() throws IOException {
        File modelFile = new File(FileType.NET_MODEL.get());
        if (!modelFile.exists()) {
            LOGGER.log(Level.ERROR, "ошибка в получении натренированной модели");
            throw new RuntimeException();
        }
        return ModelSerializer.restoreMultiLayerNetwork(modelFile);
    }

    private INDArray imageToINDArray(Mat image) {
        // Преобразуйте Mat в 4D-матрицу в формате (пакет, каналы, высота, ширина)
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

    private double cosineSimilarity(INDArray a, INDArray b) {
        INDArray aTranspose = a.transpose();
        INDArray dotProduct = a.mmul(aTranspose); // Замена функции a.dot(b.transpose())
        double dotProductValue = dotProduct.getDouble(0);
        double normA = a.norm2().getDouble(0);
        double normB = b.norm2().getDouble(0);
        return dotProductValue / (normA * normB);
    }

    public void findOnVideo() {

        // загрузка файла каскадного классификатора для обнаружения лиц
        CascadeClassifier faceDetector = new CascadeClassifier(HaarcascadeType.FRONTALFACE.get());
        VideoCapture camera = new VideoCapture(FileType.VIDEO.get());
        Mat frame = new Mat();

        while (camera.read(frame)) {

            // преобразование изображения в черно-белое
            Mat grayFrame = new Mat();
            Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);

            // обнаружение лиц
            MatOfRect faceDetections = new MatOfRect();
            faceDetector.detectMultiScale(grayFrame, faceDetections);

            // отрисовка прямоугольников вокруг лиц
            for (Rect rect : faceDetections.toArray()) {
                Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
            }

            // отображение кадра с прямоугольниками
            HighGui.imshow("Face Detection", frame);
            HighGui.waitKey(1);

        }
    }

    private void test() throws IOException {
        Vision main = new Vision();
        Mat mat1 = Imgcodecs.imread("src/main/resources/templates/person_img/1.jpg");
        Mat mat2 = Imgcodecs.imread("src/main/resources/templates/person_img/t1.jpg");

        main.compareFaces(main.detectFaces(mat1), main.detectFaces(mat2));
    }
}
