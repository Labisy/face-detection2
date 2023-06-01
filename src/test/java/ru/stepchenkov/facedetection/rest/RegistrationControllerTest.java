package ru.stepchenkov.facedetection.rest;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.stepchenkov.rest.controller.RegistrationController;
import ru.stepchenkov.rest.model.RegistrationModel;
import ru.stepchenkov.rest.service.RegistrationService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@SpringBootTest
class RegistrationControllerTest {
    @Mock
    private RegistrationService service;
    @InjectMocks
    private RegistrationController controller;

    @Test
    void registrationTest() {
        RegistrationModel model = new RegistrationModel();
        model.setDate(LocalDate.now());
        model.setPhoto(getArrPhoto());

        service.registration(model);
    }

    private byte[] getArrPhoto() {
        try {
          return convertedFileToByteArr();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] convertedFileToByteArr() throws IOException {
        // Чтение изображения из файла в буферизованное изображение
        File file = new File("src/main/resources/templates/person_img/me.jpg");
        BufferedImage image = ImageIO.read(file);

        // Создание байтового потока для записи байтов изображения
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

        // Запись изображения в байтовый массив
        ImageIO.write(image, "jpg", byteStream);

        return byteStream.toByteArray();
    }
}
