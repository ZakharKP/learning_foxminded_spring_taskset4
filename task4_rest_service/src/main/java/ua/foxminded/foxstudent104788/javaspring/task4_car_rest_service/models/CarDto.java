package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CarDto {

    public CarDto(Car car) {
	this.id = car.getId();
	this.model = car.getModel();
	this.categories = car.getCategories();
	this.make = car.getMake();
	this.year = car.getYear();
    }

    private String id;

    private String model;

    private Make make;

    private Set<Category> categories;

    private Integer year;

}
