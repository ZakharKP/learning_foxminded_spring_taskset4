package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.Serializable;

import javax.persistence.EntityExistsException;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.query.spi.QueryImplementor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.Car;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.services.CarIdGenerator;

@SpringBootTest(classes = CarIdGenerator.class)
class CarIdGeneratorTest {

    @Autowired
    private CarIdGenerator carIdGenerator;

    @MockBean
    private SharedSessionContractImplementor session;

    @MockBean
    private QueryImplementor<Car> query;

    @Test
    void testGenerateWhenObjectIsNotCar() {
	
	Object object = new Object();

	assertThrows(EntityExistsException.class, () -> {
	    carIdGenerator.generate(null, object);
	});

    }

    @Test
    void testGenerateWhenCarIdNotNull() {
	
	Car car = new Car();
	car.setId("TEST123");

	Serializable id = carIdGenerator.generate(null, car);
	
	assertEquals("TEST123", id);

    }

    @Test
    void testGenerateWhenCarIdIsNullNotExist() {
	
	Car car = new Car();
	when(session.createQuery(anyString(), eq(Car.class))).thenReturn(query);
	when(query.setParameter(anyString(), anyString())).thenReturn(query);
	when(query.uniqueResult()).thenReturn(null);

	Serializable id = carIdGenerator.generate(session, car);
	
	assertNotNull(id);
	verify(session).createQuery(anyString(), eq(Car.class));
	verify(query).setParameter(anyString(), anyString());
	verify(query).uniqueResult();

    }

    @Test
    void testGenerateWithExistingCarId() {
	Car car = new Car();
	when(session.createQuery(anyString(), eq(Car.class))).thenReturn(query);
	when(query.setParameter(anyString(), anyString())).thenReturn(query);
	when(query.uniqueResult()).thenReturn(car);
	when(session.createQuery(anyString(), eq(Car.class))).thenReturn(query);
	when(query.setParameter(anyString(), anyString())).thenReturn(query);
	when(query.uniqueResult()).thenReturn(null);

	Serializable id = carIdGenerator.generate(session, car);
	
	assertNotNull(id);
	verify(session).createQuery(anyString(), eq(Car.class));
	verify(query).setParameter(anyString(), anyString());
	verify(query).uniqueResult();
	verify(session).createQuery(anyString(), eq(Car.class));
	verify(query).setParameter(anyString(), anyString());
	verify(query).uniqueResult();

    }

}
