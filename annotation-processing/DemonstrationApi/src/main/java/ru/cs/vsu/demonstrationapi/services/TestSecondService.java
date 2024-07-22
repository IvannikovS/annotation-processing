package ru.cs.vsu.demonstrationapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cs.vsu.demonstrationapi.repositories.TestSecondRepository;

@Service
public class TestSecondService {
    private final TestSecondRepository testSecondRepository;


    @Autowired
    public TestSecondService(TestSecondRepository testSecondRepository) {
        this.testSecondRepository = testSecondRepository;

    }
}
