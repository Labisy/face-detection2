package ru.stepchenkov.facedetection.rest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.stepchenkov.facedetection.rest.api.ApiControllerTest;
import ru.stepchenkov.rest.entity.Department;
import ru.stepchenkov.rest.service.DepartmentService;

class DepartmentControllerTest extends ApiControllerTest {

    @MockBean
    private DepartmentService departmentService;

    private Department department;

    @BeforeEach
    public void init() {
        department = new Department();
        department.setDep(155);
    }

    @Test
    @DisplayName("save new department")
    void saveTest() throws Exception {
        BDDMockito.given(departmentService.save(ArgumentMatchers.any()))
                .willAnswer(invocation -> invocation.getArgument(0));
        String json = getObjectMapper().writeValueAsString(department);

        getMockMvc().perform(MockMvcRequestBuilders.post("/department/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("delete department")
    void deleteTest() throws Exception {
        getMockMvc().perform(MockMvcRequestBuilders.delete("/department/delete/{value}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
