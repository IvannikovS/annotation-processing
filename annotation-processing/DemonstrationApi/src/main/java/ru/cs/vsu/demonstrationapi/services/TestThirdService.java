package ru.cs.vsu.demonstrationapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.cs.vsu.demonstrationapi.repositories.TestThirdRepository;

@Service
public class TestThirdService {
    private final TestThirdRepository testThirdRepository;

    @Autowired
    public TestThirdService(TestThirdRepository testThirdRepository) {
        this.testThirdRepository = testThirdRepository;
    }
}
