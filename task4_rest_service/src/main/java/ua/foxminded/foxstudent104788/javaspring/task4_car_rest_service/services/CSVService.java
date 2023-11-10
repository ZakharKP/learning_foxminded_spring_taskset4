package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface CSVService {

    void saveFromCSVToRepository(MultipartFile file);

}
