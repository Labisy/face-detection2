package ru.stepchenkov.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stepchenkov.rest.entity.Department;
import ru.stepchenkov.rest.entity.Info;
import ru.stepchenkov.rest.entity.Log;
import ru.stepchenkov.rest.entity.Photo;
import ru.stepchenkov.rest.entity.Post;
import ru.stepchenkov.rest.entity.Time;
import ru.stepchenkov.rest.entity.User;
import ru.stepchenkov.rest.model.RegistrationModel;
import ru.stepchenkov.rest.repo.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistrationService {
    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    private InfoRepo infoRepo;
    @Autowired
    private PhotoRepo photoRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private TimeRepo timeRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private LogRepo logRepo;

    public RegistrationModel registration(RegistrationModel model) {
        save(model);
        return model;
    }

    public void save(RegistrationModel model) {
        Department department = getDepartment(model);
        Post post = getPost(model);
        Photo photo = savePhoto(model);
        Time time = saveTime();
        User user = saveUser(model);
        saveLog(user);
        saveInfo(department, post, photo, user, time, model.getPhone());
    }


    public Department getDepartment(RegistrationModel model) {
        return departmentRepo.findByDep(model.getDep());
    }

    public Post getPost(RegistrationModel model) {
        return postRepo.getPostByPostName(model.getPostName());
    }

    public Photo savePhoto(RegistrationModel model) {
        Photo photo = new Photo();
        photo.setImage(model.getPhoto());
        return photoRepo.save(photo);
    }

    public User saveUser(RegistrationModel model) {
        User user = new User();
        user.setFirstName(model.getFirstName());
        user.setLastName(model.getLastName());
        if (model.getThirdName() != null) user.setThirdName(model.getThirdName());
        user.setServiceNumber(model.getServiceNumber());

        return userRepo.save(user);
    }

    public Time saveTime() {
        Time time = new Time();
        time.setDate(LocalDate.now());
        return timeRepo.save(time);
    }

    public Log saveLog(User user) {
        Log log = new Log();
        log.setPersonId(user);
        log.setTime(LocalDateTime.now());
        log.setInfo("Create new user");
        return logRepo.save(log);
    }

    public Info saveInfo(Department dep, Post post, Photo photo, User user, Time time, String phone) {
        Info info = new Info();
        info.setDep(dep);
        info.setPost(post);
        info.setPhoto(photo);
        info.setUser(user);
        info.setTime(List.of(time));
        info.setPhone(phone);

        return infoRepo.save(info);
    }

}
