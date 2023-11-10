package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.Car;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.CarDto;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.CarDtoFilter;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.repositories.CarRepository;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.services.impl.CarServiceImpl;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.testdata.ForTestDataCreator;

@SpringBootTest(classes = CarServiceImpl.class)
class CarServiceImplTest {

    @Autowired
    private CarServiceImpl service;

    @MockBean
    private CarRepository repository;

    @Test
    void testCreateCar() {

	Car car = ForTestDataCreator.getNewCar();
	when(repository.save(any(Car.class))).thenReturn(car);
	CarDto expected = new CarDto(car);

	CarDto actual = service.createCar(expected);

	verify(repository).save(car);
	assertEquals(expected, actual);
    }

    @Test
    void testUpdateCarCarDto() {
	
	Car car = ForTestDataCreator.getCar();
	when(repository.findById(anyString())).thenReturn(Optional.of(car));
	when(repository.save(any(Car.class))).thenReturn(car);
	CarDto expected = new CarDto(car);

	CarDto actual = service.updateCar(expected);

	verify(repository).findById(car.getId());
	verify(repository).save(car);
	assertEquals(expected, actual);
    }

    @Test
    void testDeleteCarCarId() {

	Car car = ForTestDataCreator.getCar();
	when(repository.findById(anyString())).thenReturn(Optional.of(car));
	doNothing().when(repository).delete(any(Car.class));

	service.deleteCar(car.getId());

	verify(repository).findById(car.getId());
	verify(repository).delete(car);
    }

    @SuppressWarnings("unchecked") @Test
    void testGetPageCarDtosWithLike() {
	
	CarDtoFilter filter = ForTestDataCreator.getCarDtoFilter();
	Page<Car> pageCar = ForTestDataCreator.getPageCar(filter);
	Page<CarDto> expected = ForTestDataCreator.getConvertPageToPageCarDto(pageCar);
	when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(pageCar);

	Page<CarDto> actual = service.getPageCarDtos(filter);

	verify(repository).findAll(any(Specification.class), any(Pageable.class));
	assertEquals(expected, actual);
    }

}
