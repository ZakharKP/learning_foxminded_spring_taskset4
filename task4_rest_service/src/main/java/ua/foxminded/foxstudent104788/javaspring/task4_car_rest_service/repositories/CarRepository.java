package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, String>, JpaSpecificationExecutor<Car> {

}
