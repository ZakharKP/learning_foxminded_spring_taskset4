package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.services;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.util.CSVHelper;

@Service
@Slf4j
public class OnStartService implements Runnable {

    @Autowired
    private CarService carService;

    @Autowired
    private CSVService csvService;

    public static final Path CSVDirectoryPath = Paths.get("src", "main", "resources", "csv");

    @PostConstruct
    @Override
    public void run() {
	log.info("Start db checking");
	if (isdbEmpty()) {
	    log.info("DB is emty - start fill in");

	    fillInDb();

	}
    }

    private void fillInDb() {
	log.info("Start store db data from local storage");
	try (DirectoryStream<Path> stream = Files.newDirectoryStream(CSVDirectoryPath)) {

	    for (Path path : stream) {
		MultipartFile multipartFile = CSVHelper.getMultipartFileFromLocalStorage(path);
		if (multipartFile != null) {
		    csvService.saveFromCSVToRepository(multipartFile);
		}
	    }
	} catch (IOException e) {
	    log.error(e.getLocalizedMessage());
	    e.printStackTrace();
	}

    }

    private boolean isdbEmpty() {
	log.info("check is db empty");
	return carService.countCars() == 0;
    }

}
