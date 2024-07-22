package ru.cs.vsu.demonstrationapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.cs.vsu.demonstrationapi.services.TestFirstService;

@RestController
public class TestFirstController {
    private final TestFirstService testFirstService;


    @Autowired
    public TestFirstController(TestFirstService testFirstService) {
        this.testFirstService = testFirstService;
    }
}
