package ru.stepchenkov.facedetection.rest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import ru.stepchenkov.facedetection.rest.api.ApiControllerTest;
import ru.stepchenkov.rest.model.RegistrationModel;
import ru.stepchenkov.rest.service.RegistrationService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RegistrationControllerTest extends ApiControllerTest {

    @MockBean
    private RegistrationService service;

    private RegistrationModel model;

    @BeforeEach
    public void init() {
        model = new RegistrationModel();
        model.setFirstName("Андрей");
        model.setLastName("Степченков");
        model.setThirdName(null);
        model.setServiceNumber(1);
        model.setDep(143);
        model.setPhoto("test");
        model.setPhone("89537356392");
        model.setPostName("рабочий");
    }

    @Test
    @DisplayName("save new Person in database")
    void registration() throws Exception {
        given(service.registration(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        String json = getObjectMapper().writeValueAsString(model);

        getMockMvc().perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

}