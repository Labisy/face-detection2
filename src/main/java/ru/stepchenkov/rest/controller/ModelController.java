package ru.stepchenkov.rest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModelController {

    @GetMapping("/save")
    public String getStreamPage() {
        return "save.html";
    }
}
