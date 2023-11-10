package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.util;

import java.util.UUID;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter(autoApply = true)
@Component
public class ObjectIdConverter implements AttributeConverter<ObjectId, UUID>{

	@Override
	public UUID convertToDatabaseColumn(ObjectId attribute) {
		log.info("Start convert ObjectId to String");
		return UUID.fromString(attribute.toString());
	}

	@Override
	public ObjectId convertToEntityAttribute(UUID dbData) {
		log.info("Start convert String to ObjectId");
		return new ObjectId(dbData.toString());
	}

}
