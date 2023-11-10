package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.controllers.CarController;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.repositories.CarRepository;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.services.CSVService;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.services.CarService;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.services.OnStartService;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.util.CSVHelper;

@SpringBootTest
class Task4CarRestServiceApplicationTests {

    @Autowired
    private CSVHelper helper;

    @Autowired
    private OnStartService startService;

    @Autowired
    private CSVService csvService;

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarController restController;

    @Test
    void contextLoadsCSVHepler() {
	assertNotNull(helper);
    }

    @Test
    void contextLoadsOnStartService() {
	assertNotNull(startService);
    }

    @Test
    void contextLoadsCSVService() {
	assertNotNull(csvService);
    }

    @Test
    void contextLoadsCarService() {
	assertNotNull(carService);
    }

    @Test
    void contextLoadsCarRepository() {
	assertNotNull(carRepository);
    }

    @Test
    void contextLoadsCarRestController() {
	assertNotNull(restController);
    }

}
