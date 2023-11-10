package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models;

import java.util.Arrays;

public enum Category {

    SUV1992("SUV1992"),
    CONVERTIBLE("Convertible"),
    VAN_MINIVAN("Van/Minivan"),
    SUV2020("SUV2020"),
    SUV("SUV"),
    PICKUP("Pickup"),
    COUPE("Coupe"),
    WAGON("Wagon"),
    HATCHBACK("Hatchback"),
    SEDAN("Sedan");

    private String categoryName;

    Category(String categoryeName) {
	this.categoryName = categoryeName;
    }

    public String getCategoryName() {
	return categoryName;
    }

    public static Category get(String categoryName) {
	return Arrays.stream(Category.values())
		.filter(x -> x.getCategoryName().equals(categoryName))
		.findFirst()
		.orElse(null);
    }
}
