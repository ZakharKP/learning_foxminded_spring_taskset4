package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.controllers;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.CarDto;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.CarDtoFilter;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.Category;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.Make;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.SortingBy;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.services.CarService;

@RestController
@RequestMapping("/api/cars")
@Slf4j
public class CarController {

    @Autowired
    private CarService carService;

    @Operation(summary = "Get list of cars by filtering and search values", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
	    value = { @ApiResponse(
		    responseCode = "200",
		    description = "Found cars",
		    content = { @Content(
			    mediaType = "application/json",
			    schema = @Schema(implementation = Page.class)) }) })
    @GetMapping()
    public ResponseEntity<Page<CarDto>> getCars(
	    @RequestParam(required = false, name = "sorting", defaultValue = "YEAR") SortingBy sorting,
	    @RequestParam(required = false, name = "sorting_direction", defaultValue = "ASC") Sort.Direction direction,
	    @RequestParam(required = false, name = "make") Make make,
	    @RequestParam(required = false, name = "category") Category category,
	    @RequestParam(required = false, name = "year") Integer year,
	    @RequestParam(required = false, name = "maxYear") Integer maxYear,
	    @RequestParam(required = false, name = "minYear") Integer minYear,
	    @RequestParam(required = false, name = "searching") String searchArgument,
	    @RequestParam(required = false, name = "page_size", defaultValue = "20") int pageSize,
	    @RequestParam(required = false, name = "page", defaultValue = "0") int page) {
	log.info("Collect {} Cars like {} for page {} sorted by {}", pageSize, searchArgument, page, sorting);
	CarDtoFilter filter = CarDtoFilter.builder()
		.sorting(sorting)
		.direction(direction)
		.make(make)
		.category(category)
		.year(year)
		.maxYear(maxYear)
		.minYear(minYear)
		.searchArgument(searchArgument)
		.pageSize(pageSize)
		.page(page)
		.build();

	return ResponseEntity.ok(carService.getPageCarDtos(filter));
    }

    @Operation(summary = "Get a car by its id", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
	    value = {
		    @ApiResponse(
			    responseCode = "200",
			    description = "Found the car",
			    content = { @Content(
				    mediaType = "application/json",
				    schema = @Schema(implementation = CarDto.class)) }),
		    @ApiResponse(responseCode = "404", description = "Car not found", content = @Content) })
    @GetMapping("/{carId}")
    public ResponseEntity<CarDto>
	    getCar(@Parameter(description = "id of car to be searched") @PathVariable String carId) {
	log.info("Start search Car with id=" + carId);

	CarDto car = carService.getCarDto(carId);
	if (car != null) {
	    log.info("Car was founded id=" + carId);

	    return ResponseEntity.ok(car);
	}
	log.info("Can't find Car id=" + carId);

	return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Create new Car", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
	    value = {
		    @ApiResponse(
			    responseCode = "201",
			    description = "Car created - return new Car(CarDto implementation)",
			    content = { @Content(
				    mediaType = "application/json",
				    schema = @Schema(implementation = CarDto.class)) }),
		    @ApiResponse(
			    responseCode = "500",
			    description = "Can't create new Car - Unknown Error",
			    content = @Content) })
    @PostMapping("/make/{make}/model/{model}/year/{year}")
    public ResponseEntity<CarDto> createCar(@PathVariable Make make, @PathVariable String model,
	    @PathVariable Integer year, @RequestParam(name = "categories") Category... categories) {

	CarDto carDto = CarDto.builder()
		.make(make)
		.model(model)
		.year(year)
		.categories(Arrays.asList(categories).stream().collect(Collectors.toSet()))
		.build();
	log.info("Start save new Car: " + carDto);

	CarDto newCar = carService.createCar(carDto);

	if (newCar != null) {

	    log.info("New Car was saved: " + newCar);
	    return ResponseEntity.status(HttpStatus.CREATED).body(newCar);
	}

	log.info("Can't save new car: " + carDto);
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Operation(summary = "Update Car", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
	    value = {
		    @ApiResponse(
			    responseCode = "200",
			    description = "Car updated - return updated Car(CarDto implementation)",
			    content = { @Content(
				    mediaType = "application/json",
				    schema = @Schema(implementation = CarDto.class)) }),
		    @ApiResponse(
			    responseCode = "500",
			    description = "Can't update Car - Unknown Error",
			    content = @Content) })
    @PutMapping("/id/{id}/make/{make}/model/{model}/year/{year}")
    public ResponseEntity<CarDto> updateCar(@PathVariable String id, @PathVariable Make make,
	    @PathVariable String model, @PathVariable Integer year,
	    @RequestParam(name = "categories") Category... categories) {

	CarDto carDto = CarDto.builder()
		.id(id)
		.make(make)
		.model(model)
		.year(year)
		.categories(Arrays.asList(categories).stream().collect(Collectors.toSet()))
		.build();

	log.info("Start update new Car: " + carDto);

	CarDto updatedCar = carService.updateCar(carDto);
	if (updatedCar != null) {

	    log.info("Car was updated: " + updatedCar);
	    return ResponseEntity.ok(updatedCar);
	}

	log.info("Can't update car: " + carDto);
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Operation(summary = "Delete Car by its id", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = { @ApiResponse(responseCode = "204", description = "car was deleted", content = @Content) })
    @DeleteMapping("/{carId}")
    public ResponseEntity<Void>
	    deleteCar(@Parameter(description = "id of car to be deleted") @PathVariable String carId) {
	log.info("Start Deleting Car: " + carId);

	carService.deleteCar(carId);

	return ResponseEntity.noContent().build();
    }
}
