package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models;

import java.util.Arrays;

public enum SortingBy {

    MAKE("make"), YEAR("year"), MODEL("model"), CATEGORY("category");

    private String sortingByName;

    SortingBy(String sortingByeName) {
	this.sortingByName = sortingByeName;
    }

    public String getSortingByName() {
	return sortingByName;
    }

    public static SortingBy get(String sortingByName) {
	return Arrays.stream(SortingBy.values())
		.filter(x -> x.getSortingByName().equalsIgnoreCase(sortingByName))
		.findFirst()
		.orElse(null);
    }

}
