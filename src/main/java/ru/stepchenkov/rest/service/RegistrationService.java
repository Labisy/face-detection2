package ru.stepchenkov.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stepchenkov.rest.entity.*;
import ru.stepchenkov.rest.model.RegistrationModel;
import ru.stepchenkov.rest.repo.*;

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

    public void registration(RegistrationModel model) {
        departmentRepo.save(getDepartment(model));
        photoRepo.save(getPhoto(model));
        timeRepo.save(getTime(model));
        userRepo.save(getUser(model));
        logRepo.save(getLog(model));
        infoRepo.save(getInfo(model));
    }


    private Department getDepartment(RegistrationModel model) {
        return departmentRepo.findByDep(model.getDep());
    }

    private Post getPost(RegistrationModel model) {
        return postRepo.getPostByPostName(model.getPostName());
    }

    private Photo getPhoto(RegistrationModel model) {
        Photo photo = new Photo();
        photo.setImage(model.getPhotoId());
        return photo;
    }

    private User getUser(RegistrationModel model) {
        User user = new User();
        user.setFirstName(model.getFirstName());
        user.setLastName(model.getLastName());
        if (!model.getThirdName().equals("")) user.setThirdName(model.getThirdName());
        user.setServiceNumber(model.getServiceNumber());

        return user;
    }

    private Time getTime(RegistrationModel model) {
        Time time = new Time();
        time.setDate(model.getDate());
        return time;
    }

    private Log getLog(RegistrationModel model) {
        Log log = new Log();
        log.setPersonId(getUser(model));
        log.setTime(LocalDateTime.now());
        log.setInfo("Create new user");
        return log;
    }

    private Info getInfo(RegistrationModel model) {
        Info info = new Info();
        info.setDep(getDepartment(model));
        info.setPost(getPost(model));
        info.setPhoto(getPhoto(model));
        info.setUser(getUser(model));
        info.setTime(List.of(getTime(model)));
        info.setPhone(model.getPhone());

        return info;
    }

}
