package ru.cs.vsu.demonstrationapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import ru.cs.vsu.demonstrationapi.services.TestThirdService;

@RestController
public class TestThirdController {
    private final TestThirdService testThirdService;

    @Autowired
    public TestThirdController(TestThirdService testThirdService) {
        this.testThirdService = testThirdService;
    }
}
