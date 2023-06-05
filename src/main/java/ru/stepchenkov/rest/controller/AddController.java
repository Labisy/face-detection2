package ru.stepchenkov.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddController {

    @GetMapping("/add")
    public String getStreamPage() {
        return "add.html";
    }

}
