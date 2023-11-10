package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models;

import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CarDtoFilter {

    private SortingBy sorting;
    private Sort.Direction direction;
    private Make make;
    private Category category;
    private String searchArgument;
    private Integer year;
    private Integer maxYear;
    private Integer minYear;
    private int pageSize;
    private int page;

}
