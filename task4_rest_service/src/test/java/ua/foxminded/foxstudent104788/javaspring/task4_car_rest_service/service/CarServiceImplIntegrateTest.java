package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.Car;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.CarDto;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.CarDtoFilter;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.SortingBy;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.repositories.CarRepository;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.services.impl.CarServiceImpl;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.testdata.ForTestDataCreator;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.util.CSVHelper;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE, classes = {
	CarRepository.class, CarServiceImpl.class }))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan("ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.repositories.CarRepository")
class CarServiceImplIntegrateTest {

    @Autowired
    private CarServiceImpl service;

    @Autowired
    private CarRepository repository;

    @BeforeEach
    void fillInDB() throws IOException {
	repository.deleteAll();
	repository.saveAll(CSVHelper.csvToCars(
		CSVHelper.getMultipartFileFromLocalStorage(Paths.get("src", "test", "resources", "csv", "test.csv"))
			.getInputStream()));
    }

    @Test
    void testCreateCar() {

	CarDto newCar = new CarDto(ForTestDataCreator.getNewCar());
	String newCarId = newCar.getId();
	
	CarDto savedCar = service.createCar(newCar);
	Car newCarEntity = repository.findById(savedCar.getId()).get();

	assertNull(newCarId);
	assertNotNull(savedCar.getId());	
	assertEquals(newCar.getCategories(), newCarEntity.getCategories());
	assertEquals(newCar.getMake(), newCarEntity.getMake());
	assertEquals(newCar.getModel(), newCarEntity.getModel());
	assertEquals(newCar.getYear(), newCarEntity.getYear());

    }

    @Test
    void testUpdateCarCarDto() {
	
	Car car = repository.findAll().stream().findAny().get();
	String oldModel = car.getModel();
	CarDto carForUpdate = new CarDto(car);
	String newModel = "test";
	
	carForUpdate.setModel(newModel);
	CarDto updatedCar = service.updateCar(carForUpdate);
	car = repository.findById(car.getId()).get();
	
	assertNotEquals(newModel, oldModel);
	assertEquals(carForUpdate, updatedCar);
	assertEquals(newModel, car.getModel());
    }

    @Test
    void testDeleteCarString() {
	
	Car car = repository.findAll().stream().findAny().get();

	service.deleteCar(car.getId());
	Optional<Car> deletedCar = repository.findById(car.getId());

	assertFalse(deletedCar.isPresent());
    }

    @Test
    void testGetPageCarDtos() {

	Car car = repository.findAll().stream().findAny().get();
	CarDtoFilter filter = CarDtoFilter.builder()
		.category(car.getCategories().stream().findAny().get())
		.direction(Sort.Direction.ASC)
		.make(car.getMake())
		.page(0)
		.pageSize(20)
		.searchArgument(car.getModel())
		.sorting(SortingBy.YEAR)
		.year(car.getYear())
		.build();

	Page<CarDto> page = service.getPageCarDtos(filter);

	assertTrue(page.getContent().contains(new CarDto(car)));
    }

    @Test
    void testSaveFromCSVToRepository() {

	repository.deleteAll();
	List<Car> before = repository.findAll();

	service.saveFromCSVToRepository(
		CSVHelper.getMultipartFileFromLocalStorage(Paths.get("src", "test", "resources", "csv", "test.csv")));
	List<Car> actual = repository.findAll();

	assertNotEquals(before, actual);

    }

    @Test
    void testGetCarDto() {

	Car car = repository.findAll().stream().findAny().get();
	CarDto expected = new CarDto(car);

	CarDto actual = service.getCarDto(car.getId());

	assertEquals(expected, actual);
    }

}
