package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.testdata;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.Car;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.CarDto;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.CarDtoFilter;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.Category;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.Make;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.SortingBy;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.util.CSVHelper;

public class ForTestDataCreator {

    public static Car getNewCar() {
	Car car = getCar();
	car.setId(null);
	return car;
    }

    public static Car getCar() {
	// TODO Auto-generated method stub
	return Car.builder()
		.id("TEST123")
		.categories(getCategoriesSet())
		.model("156")
		.make(Make.ALFA_ROMEO)
		.year(2004)
		.build();
    }

    public static Set<Category> getCategoriesSet() {
	Set<Category> categories = new HashSet<>();
	categories.add(Category.COUPE);
	categories.add(Category.HATCHBACK);
	categories.add(Category.SEDAN);
	categories.add(Category.WAGON);
	return categories;
    }

    public static CarDtoFilter getCarDtoFilter() {
	return CarDtoFilter.builder()
		.category(Category.COUPE)
		.direction(Sort.Direction.ASC)
		.searchArgument("test")
		.make(Make.ALFA_ROMEO)
		.year(2004)
		.sorting(SortingBy.CATEGORY)
		.page(0)
		.pageSize(20)
		.build();
    }

    public static CarDtoFilter getCarDtoFilterLikeNull() {
	CarDtoFilter filter = getCarDtoFilter();
	filter.setSearchArgument(null);
	return filter;
    }

    public static Page<Car> getPageCar(CarDtoFilter filter) {
	return new PageImpl<Car>(getCars(),
		PageRequest.of(filter.getPage(),
			filter.getPageSize(),
			Sort.by(filter.getDirection(), filter.getSorting().getSortingByName())),
		20);
    }

    private static List<Car> getCars() {
	MultipartFile file = getMultipartFile();
	try {
	    return CSVHelper.csvToCars(file.getInputStream());
	} catch (IOException e) {
	    e.printStackTrace();
	    return null;
	}
    }

    public static MultipartFile getMultipartFile() {
	return CSVHelper.getMultipartFileFromLocalStorage(Paths.get("src", "test", "resources", "csv", "test.csv"));
    }

    public static Page<CarDto> getConvertPageToPageCarDto(Page<Car> page) {
	return new PageImpl<CarDto>(page.getContent().stream().map(CarDto::new).collect(Collectors.toList()),
		page.getPageable(),
		page.getTotalElements());

    }

}
