package ru.stepchenkov.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.stepchenkov.rest.service.DataService;

import java.io.IOException;

@Controller
public class DataController {
    @Autowired
    DataService service;

    @GetMapping("/data")
    public String getStreamPage() throws IOException {
        service.addNewRow();
        service.readFile();
        return "table.html";
    }
}
