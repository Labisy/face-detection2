package ru.stepchenkov.facedetection.rest.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import ru.stepchenkov.rest.entity.*;
import ru.stepchenkov.rest.model.RegistrationModel;
import ru.stepchenkov.rest.repo.*;
import ru.stepchenkov.rest.service.RegistrationService;

import java.time.LocalDate;
import java.util.List;

class RegistrationServiceTest {
    @InjectMocks
    private RegistrationService registrationService;

    @Mock
    private UserRepo userRepo;
    @Mock
    private DepartmentRepo departmentRepo;
    @Mock
    private PhotoRepo photoRepo;
    @Mock
    private PostRepo postRepo;
    @Mock
    private TimeRepo timeRepo;
    @Mock
    private InfoRepo infoRepo;

    private RegistrationModel model;

    public RegistrationServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

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
    @DisplayName("save user in database")
    void saveUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Андрей");
        user.setLastName("Степченков");
        user.setThirdName(null);
        user.setServiceNumber(1);

        Mockito.when(userRepo.save(ArgumentMatchers.any())).thenReturn(user);
        User response = registrationService.saveUser(model);

        Assertions.assertThat(response)
                .isNotNull()
                .extracting("serviceNumber", "firstName", "lastName")
                .contains(1, "Андрей", "Степченков");
    }

    @Test
    @DisplayName("get department from database")
    void getDep() {
        Department dep = new Department();
        dep.setDep(143);

        Mockito.when(departmentRepo.findByDep(143)).thenReturn(dep);
        Department response = registrationService.getDepartment(model);

        Assertions.assertThat(response)
                .isNotNull()
                .extracting(Department::getDep)
                .isEqualTo(143);
    }

    @Test
    @DisplayName("save photo in database")
    void savePhoto() {
        Photo photo = new Photo();
        photo.setImage("path");

        Mockito.when(photoRepo.save(ArgumentMatchers.any())).thenReturn(photo);
        Photo response = registrationService.savePhoto(model);

        Assertions.assertThat(response)
                .isNotNull()
                .extracting(Photo::getImage)
                .isEqualTo("path");
    }

    @Test
    @DisplayName("get post from model and check into database")
    void getPost() {
        Post post = new Post();
        post.setPostName("Рабочий");

        Mockito.when(postRepo.getPostByPostName(ArgumentMatchers.any())).thenReturn(post);
        Post response = registrationService.getPost(model);

        Assertions.assertThat(response)
                .isNotNull()
                .extracting(Post::getPostName)
                .isEqualTo("Рабочий");
    }

    @Test
    @DisplayName("save date and time in database")
    void saveTime() {
        Time expected = new Time();
        expected.setDate(LocalDate.now());

        Mockito.when(timeRepo.save(ArgumentMatchers.any())).thenReturn(expected);
        Time response = registrationService.saveTime();

        Assertions.assertThat(response)
                .isNotNull()
                .extracting(Time::getDate)
                .isEqualTo(LocalDate.now());

    }

    @Test
    @DisplayName("save Info in database")
    void saveInfo() {
        Department department = new Department();
        department.setDep(123);
        Post post = new Post();
        post.setPostName("Рабочий");
        Photo photo = new Photo();
        photo.setImage("path");
        User user = new User();
        user.setFirstName("Андрей");
        Time time = new Time();
        time.setDate(LocalDate.now());

        Info expected = new Info();
        expected.setPhone("89537356392");
        expected.setDep(department);
        expected.setPost(post);
        expected.setPhoto(photo);
        expected.setUser(user);
        expected.setTime(List.of(time));

        Mockito.when(infoRepo.save(ArgumentMatchers.any())).thenReturn(expected);
        Info response = registrationService.saveInfo(new Department(), new Post(), new Photo(), new User(), new Time(), "89537356392");

        Assertions.assertThat(response)
                .isNotNull()
                .extracting("dep", "post", "photo", "user", "phone")
                .contains(department, post, photo, user, "89537356392");
    }
}
