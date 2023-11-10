package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.configurations.PgSQLEnumType;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "cars")
@TypeDef(name = "make", typeClass = PgSQLEnumType.class)
@TypeDef(name = "category", typeClass = PgSQLEnumType.class)
public class Car {

    @Id
    @GenericGenerator(
	    name = "car-id-generator",
	    strategy = "ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.services.CarIdGenerator")
    @GeneratedValue(generator = "car-id-generator")
    @Column(name = "car_id")
    private String id;

    @Column(name = "car_model")
    private String model;

    @Column(name = "car_make")
    @Enumerated(EnumType.STRING)
    @Type(type = "make")
    private Make make;

    @ElementCollection(targetClass = Category.class)
    @CollectionTable(name = "car_categories", joinColumns = @JoinColumn(name = "car_id"))
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    @Type(type = "category")
    private Set<Category> categories;

    @Column(name = "car_year")
    private Integer year;
}
