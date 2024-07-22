package ru.cs.vsu.demonstrationapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.cs.vsu.demonstrationapi.services.TestSecondService;

@RestController
public class TestSecondController {
    private final TestSecondService testSecondService;

    @Autowired
    public TestSecondController(TestSecondService testSecondService) {
        this.testSecondService = testSecondService;
    }
}
