package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.repositories.CarRepository;

@SpringBootTest
class OnStartServiceTest {

    @Autowired
    private CarRepository repository;

    @Test
    void testRun() {
	assertTrue(repository.findAll().size() != 0);
    }

}
