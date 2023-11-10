package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.Car;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.CarDto;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.CarDtoFilter;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.repositories.CarRepository;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.services.CSVService;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.services.CarService;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.util.CSVHelper;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.util.CarSpecificationsCreator;

@Service
@Slf4j
public class CarServiceImpl implements CarService, CSVService {

    @Autowired
    private CarRepository repository;

    @Override
    public Car saveNewCar(Car car) {
	log.info("Saving new Car" + car);
	return repository.save(car);
    }

    @Override
    public List<Car> saveAllCars(List<Car> cars) {
	log.info("Saving {} new Cars", cars.size());
	return repository.saveAll(cars);
    }

    @Override
    public Optional<Car> getCar(String id) {
	log.info("Searching for Car with id=" + id);
	Optional<Car> optional = repository.findById(id);

	if (optional.isPresent()) {
	    log.info("Car was finded " + optional.get());
	    return optional;
	}

	log.info("Can't find Car with id=" + id);
	return Optional.empty();
    }

    @Override
    public List<Car> getAll() {
	log.info("Collect all Cars");

	return repository.findAll();
    }

    @Override
    public Car updateCar(Car car) {
	log.info("Upate Car:" + car);
	return repository.save(car);
    }

    @Override
    public void deleteCar(Car car) {
	log.info("Deleting Car" + car);
	repository.delete(car);
    }

    @Override
    public void deleteListOfCars(List<Car> cars) {
	log.info("Deleting List of {} Cars", cars.size());
	repository.deleteAll(cars);
    }

    @Override
    public long countCars() {
	log.info("Counting all Cars");
	return repository.count();
    }

    @Override
    public CarDto createCar(CarDto carDto) {
	log.info("Creating new car: " + carDto);

	Car car = saveNewCar(Car.builder()
		.categories(carDto.getCategories())
		.model(carDto.getModel())
		.make(carDto.getMake())
		.year(carDto.getYear())
		.build());

	log.info("Car was created: " + car);
	return new CarDto(car);
    }

    @Override
    public CarDto updateCar(CarDto carDto) {
	log.info("Start Car updating : " + carDto);

	Optional<Car> carOptional = getCar(carDto.getId());

	if (carOptional.isPresent()) {
	    Car car = carOptional.get();
	    if (!car.getCategories().equals(carDto.getCategories())) {
		log.info("Update car category from \"{}\" to \"{}\"", car.getCategories(), carDto.getCategories());
		car.setCategories(carDto.getCategories());
	    }
	    if (!car.getMake().equals(carDto.getMake())) {
		log.info("Update car manufavturer from \"{}\" to \"{}\"", car.getMake(), carDto.getMake());
		car.setMake(carDto.getMake());
	    }
	    if (!car.getModel().equals(carDto.getModel())) {
		log.info("Update car Model from \"{}\" to \"{}\"", car.getModel(), carDto.getModel());
		car.setModel(carDto.getModel());
	    }
	    if (!car.getYear().equals(carDto.getYear())) {
		log.info("Update car category from \"{}\" to \"{}\"", car.getYear(), carDto.getYear());
		car.setYear(carDto.getYear());
	    }
	    car = updateCar(car);
	    log.info("Car was Upated: " + car);
	    return new CarDto(car);
	}
	log.error("Can't find Car with id:" + carDto.getId());
	log.info("Can't update car, can't find car :" + carDto);
	return null;
    }

    @Override
    public void deleteCar(String carId) {
	log.info("Start Delete Car : " + carId);
	Optional<Car> carOptional = getCar(carId);

	if (carOptional.isPresent()) {
	    Car car = carOptional.get();
	    deleteCar(car);
	    log.info(carId + " - was deleted");
	} else {
	    log.error("Can't proceed deleting operation - can't find car:" + carId);
	}
    }

    @Override
    public Page<CarDto> getPageCarDtos(CarDtoFilter filter) {
	log.info("Get List of CarDtos for filter: " + filter);
	Page<Car> page;

	Specification<Car> specification = getSpecification(filter);
	Pageable pageable = getPageable(filter);

	page = repository.findAll(specification, pageable);
	return convertToPageCarDto(page);
    }

    private Specification<Car> getSpecification(CarDtoFilter filter) {
	log.info("creating specifications for filter" + filter);

	Specification<Car> likeSpecification = CarSpecificationsCreator.getTrueSpecification();

	if (filter.getSearchArgument() != null) {
	    likeSpecification = getSearchSpecification(filter);
	}

	Specification<Car> specification = likeSpecification.and(getFilterEqualSpecification(filter));

	log.info("specifications was created for filter: " + filter);
	return specification;
    }

    private Specification<Car> getFilterEqualSpecification(CarDtoFilter filter) {
	log.info("Creating Equal Specification for filter:" + filter);
	Specification<Car> specification = CarSpecificationsCreator.getTrueSpecification();

	if (filter.getMake() != null) {
	    specification = specification.and(CarSpecificationsCreator.getMakeSpecification(filter.getMake()));
	}
	if (filter.getCategory() != null) {
	    specification = specification.and(CarSpecificationsCreator.getCategorySpecification(filter.getCategory()));
	}
	if (filter.getYear() != null) {
	    specification = specification.and(CarSpecificationsCreator.getYearSpecification(filter.getYear()));
	}

	if (filter.getMinYear() != null) {
	    specification = specification
		    .and(CarSpecificationsCreator.getYearMoreEqualsSpecification(filter.getMinYear()));
	}

	if (filter.getMaxYear() != null) {
	    specification = specification
		    .and(CarSpecificationsCreator.getYearLessEqualsSpecification(filter.getMaxYear()));
	}

	log.info("Equal specifications was created for filter: " + filter);
	return specification;
    }

    private Specification<Car> getSearchSpecification(CarDtoFilter filter) {
	log.info("creating specifications for search: " + filter.getSearchArgument());

	Specification<Car> searchSpecification = CarSpecificationsCreator
		.getModelSpecifikationForSearching(filter.getSearchArgument());

	if (filter.getMake() == null) {
	    searchSpecification = searchSpecification
		    .or(CarSpecificationsCreator.getMakeSpecificationForSearching(filter.getSearchArgument()));
	}
	if (filter.getCategory() == null) {
	    searchSpecification = searchSpecification
		    .or(CarSpecificationsCreator.getCategorySpecificationForSearching(filter.getSearchArgument()));
	}
	if (filter.getYear() == null) {
	    searchSpecification = searchSpecification
		    .or(CarSpecificationsCreator.getYearSpecificationForSearching(filter.getSearchArgument()));
	}

	log.info("specifications for search was created");
	return searchSpecification;
    }

    private Pageable getPageable(CarDtoFilter filter) {
	log.info("Prepare pageable for filter: " + filter);
	return PageRequest.of(filter.getPage(),
		filter.getPageSize(),
		Sort.by(filter.getDirection(), filter.getSorting().getSortingByName()));
    }

    private Page<CarDto> convertToPageCarDto(Page<Car> page) {

	return new PageImpl<>(page.getContent().stream().map(CarDto::new).collect(Collectors.toList()),
		page.getPageable(),
		page.getTotalElements());
    }

    @Override
    public void saveFromCSVToRepository(MultipartFile file) {
	log.info("Start saving cars from file: " + file.getOriginalFilename());

	try {
	    List<Car> cars = CSVHelper.csvToCars(file.getInputStream());
	    saveAllCars(cars);
	} catch (IOException e) {
	    log.error("fail to store csv data: " + e.getMessage());
	    throw new RuntimeException("fail to store csv data: " + e.getMessage());
	}

    }

    @Override
    public CarDto getCarDto(String carId) {
	Optional<Car> car = getCar(carId);

	if (car.isPresent()) {
	    return new CarDto(car.get());
	}

	return null;
    }
}

/*
 * Car carExample = Car.builder().categories(new
 * HashSet<>()).make(filter.getMake()).year(filter.getYear()) .build();
 * 
 * carExample.getCategories().add(filter.getCategory());
 * 
 * Example<Car> example = getExamle(carExample);
 * 
 * if (filter.getLike() == null) {
 * log.info("Get page for search value == null"); page =
 * repository.findAll(example, pageable); } else {
 * log.info("Get page for search value: " + filter.getLike()); }
 */

/*
 * private Example<Car> getExamle(Car carExample) {
 * log.info("Prepare example for carArguments :" + carExample); return
 * Example.of(carExample,
 * ExampleMatcher.matching().withStringMatcher(StringMatcher.CONTAINING).
 * withIgnoreNullValues()); }
 */
