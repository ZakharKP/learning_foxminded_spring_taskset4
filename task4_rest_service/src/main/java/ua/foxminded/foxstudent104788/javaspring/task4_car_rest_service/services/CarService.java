package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.Car;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.CarDto;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.CarDtoFilter;

@Service
public interface CarService {

    /**
     * Adds a new car to the system.
     *
     * @param car The car to be added.
     * @return The car entity with rows affected if the car was added successfully,
     *         or null if an error occurred.
     */
    Car saveNewCar(Car car);

    /**
     * Adds a list of cars to the system.
     *
     * @param cars The list of cars to be added.
     * @return The List of saved cars if all cars were added successfully, or an
     *         empty List if an error occurred.
     */
    List<Car> saveAllCars(List<Car> cars);

    /**
     * Retrieves a car by its ID.
     *
     * @param id The ID of the car.
     * @return An Optional containing the car if found, or an empty Optional if the
     *         car is not found.
     */
    Optional<Car> getCar(String id);

    /**
     * Retrieves all cars from the system.
     *
     * @return A list of all cars.
     */
    List<Car> getAll();

    /**
     * Updates an existing car in the system.
     *
     * @param car The car to be updated.
     * @return The updated entity if the car was updated successfully, or null if an
     *         error occurred.
     */
    Car updateCar(Car car);

    /**
     * Deletes a car from the system.
     *
     * @param car The car to be deleted.
     */
    void deleteCar(Car car);

    /**
     * Deletes a list of cars from the system.
     *
     * @param cars The list of cars to be deleted.
     */
    void deleteListOfCars(List<Car> cars);

    /**
     * Counts the number of cars in the system.
     *
     * @return The number of cars in the system.
     */
    long countCars();

    CarDto createCar(CarDto carDto);

    CarDto updateCar(CarDto carDto);

    void deleteCar(String carId);

    Page<CarDto> getPageCarDtos(CarDtoFilter filter);

    CarDto getCarDto(String carId);

}
