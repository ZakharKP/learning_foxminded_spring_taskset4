package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

import lombok.extern.slf4j.Slf4j;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.Car;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.Category;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.Make;

@Slf4j
public class CarSpecificationsCreator {

    public static Specification<Car> getMakeSpecificationForSearching(String like) {
	log.info("Create make specification like: " + like);
	return (root, query, criteriaBuilder) -> {
	    return criteriaBuilder.like(criteriaBuilder.upper(root.get("make").as(String.class)),
		    "%" + like.toUpperCase() + "%");
	};
    }

    public static Specification<Car> getModelSpecifikationForSearching(String like) {
	log.info("Create model specification like: " + like);
	return (root, query, criteriaBuilder) -> {
	    return criteriaBuilder.like(criteriaBuilder.upper(root.get("model").as(String.class)),
		    "%" + like.toUpperCase() + "%");
	};
    }

    public static Specification<Car> getCategorySpecificationForSearching(String like) {
	log.info("Create category specification like: " + like);
	Specification<Car> specification = null;
	List<Category> categories = Arrays.asList(Category.values())
		.stream()
		.filter(c -> c.getCategoryName().toUpperCase().contains(like.toUpperCase()))
		.collect(Collectors.toList());
	for (int i = 0; i < categories.size(); i++) {
	    if (i == 0) {
		specification = getCategorySpecification(categories.get(i));
	    } else {
		specification = specification.or(getCategorySpecification(categories.get(i)));
	    }
	}

	return specification != null ? specification : getFalseSpecification();

    }

    public static Specification<Car> getYearSpecificationForSearching(String like) {
	log.info("Create year specification like: " + like);
	return (root, query, criteriaBuilder) -> {
	    return criteriaBuilder.like(
		    criteriaBuilder.upper(criteriaBuilder
			    .function("to_char", String.class, root.get("year"), criteriaBuilder.literal("yyyy"))),
		    "%" + like.toUpperCase() + "%");
	};
    }

    public static Specification<Car> getMakeSpecification(Make make) {
	log.info("Create make specification equals: " + make.getMakeName());
	return (root, query, criteriaBuilder) -> {
	    return criteriaBuilder.equal(root.get("make"), make);
	};
    }

    public static Specification<Car> getYearSpecification(Integer year) {
	log.info("Create year specification equals: " + year);
	return (root, query, criteriaBuilder) -> {
	    return criteriaBuilder.equal(root.get("year"), year);
	};
    }

    public static Specification<Car> getCategorySpecification(Category category) {
	log.info("Create category specification contains: " + category.getCategoryName());
	return (root, query, criteriaBuilder) -> {
	    return criteriaBuilder.isMember(category, root.get("categories"));
	};
    }

    public static Specification<Car> getTrueSpecification() {
	return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(criteriaBuilder.literal(true));
    }

    public static Specification<Car> getFalseSpecification() {
	return (root, query, criteriaBuilder) -> criteriaBuilder.isTrue(criteriaBuilder.literal(false));
    }

    public static Specification<Car> getYearMoreEqualsSpecification(Integer minYear) {
	log.info("Create Less year specification equals: " + minYear);

	return (root, query, criteriaBuilder) -> {
	    return criteriaBuilder.greaterThanOrEqualTo(root.get("year"), minYear);
	};
    }

    public static Specification<Car> getYearLessEqualsSpecification(Integer maxYear) {
	log.info(" Max year specification equals: " + maxYear);

	return (root, query, criteriaBuilder) -> {
	    return criteriaBuilder.lessThanOrEqualTo(root.get("year"), maxYear);
	};
    }

}
