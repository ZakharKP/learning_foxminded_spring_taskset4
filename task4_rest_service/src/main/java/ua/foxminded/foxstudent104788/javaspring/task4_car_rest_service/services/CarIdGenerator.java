package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.services;

import java.io.Serializable;
import java.util.Random;

import javax.persistence.EntityExistsException;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;

import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.Car;

@Service
public class CarIdGenerator implements IdentifierGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int STRING_LENGTH = 10;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
	if (object instanceof Car) {
	    Car car = (Car) object;
	    if (car.getId() != null) {
		return car.getId();
	    }

	} else {
	    throw new EntityExistsException("Paramether 'Object' is not instance of Car");
	}

	String id = generateCarId();
	while (isIdInUse(id, session)) {
	    id = generateCarId();
	}

	return id;
    }

    private String generateCarId() {
	StringBuilder stringBuilder = new StringBuilder(STRING_LENGTH);
	Random random = new Random();

	for (int i = 0; i < STRING_LENGTH; i++) {
	    int randomIndex = random.nextInt(CHARACTERS.length());
	    char randomChar = CHARACTERS.charAt(randomIndex);
	    stringBuilder.append(randomChar);
	}

	return stringBuilder.toString();
    }

    private boolean isIdInUse(String id, SharedSessionContractImplementor session) {
	try {

	    String hql = "FROM Car c WHERE c.id = :carId";

	    Query<Car> query = session.createQuery(hql, Car.class);
	    query.setParameter("carId", id);

	    Car car = query.uniqueResult();

	    return car != null;
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }
}
