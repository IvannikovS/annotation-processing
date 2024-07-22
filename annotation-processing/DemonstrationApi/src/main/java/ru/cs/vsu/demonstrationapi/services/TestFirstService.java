package ru.cs.vsu.demonstrationapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cs.vsu.demonstrationapi.repositories.TestFirstRepository;

@Service
public class TestFirstService {
    private final TestFirstRepository testFirstRepository;


    @Autowired
    public TestFirstService(TestFirstRepository testFirstRepository) {
        this.testFirstRepository = testFirstRepository;
    }
}
