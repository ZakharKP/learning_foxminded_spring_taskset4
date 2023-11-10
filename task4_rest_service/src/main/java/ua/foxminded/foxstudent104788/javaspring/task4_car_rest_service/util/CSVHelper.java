package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.Car;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.Category;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.Make;

@Component
@Slf4j
public class CSVHelper {

    public static String TYPE = "text/csv";
    public static String[] HEADERS = { "objectId", "Make", "Year", "Model", "Category" };

    public static boolean hasCSVFormat(MultipartFile file) {
	return TYPE.equals(file.getContentType());
    }

    public static List<Car> csvToCars(InputStream is) {
	try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		CSVParser csvParser = new CSVParser(fileReader,
			CSVFormat.DEFAULT.builder()
				.setHeader(HEADERS)
				.setSkipHeaderRecord(true)
				.setIgnoreHeaderCase(true)
				.setTrim(true)
				.build());) {

	    log.info("Start reading CSV file");

	    List<Car> cars = new ArrayList<>();

	    Iterable<CSVRecord> csvRecords = csvParser.getRecords();

	    for (CSVRecord csvRecord : csvRecords) {
		if (csvRecord.size() < HEADERS.length) {
		    log.info("Skipping invalid record: " + csvRecord.toString());
		    continue; // Skip this record and move to the next one
		}
		String id = csvRecord.get("objectId");
		String model = csvRecord.get("Model");
		String make = csvRecord.get("Make");
		String year = csvRecord.get("Year");
		String categoriesString = csvRecord.get("Category");

		if (id.isEmpty() || model.isEmpty() || make.isEmpty() || year.isEmpty() || categoriesString.isEmpty()) {
		    log.info("Skipping invalid record: " + csvRecord.toString());
		    continue; // Skip this record and move to the next one
		}

		Set<Category> categories = new HashSet<>();

		for (String category : categoriesString.replace("\"", "").split(",")) {
		    categories.add(Category.get(category));
		}

		Car car = Car.builder()
			.id(id)
			.make(Make.get(make))
			.year(Integer.parseInt(year))
			.model(model)
			.categories(categories)
			.build();

		cars.add(car);
	    }

	    return cars;
	} catch (IOException e) {
	    log.error("fail to parse CSV file: " + e.getMessage());
	    throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
	}
    }

    public static MultipartFile getMultipartFileFromLocalStorage(Path path) {
	String name = path.getFileName().toString();
	String originalFileName = path.getFileName().toString();
	String contentType = path.toString().endsWith(".csv") ? "text/csv" : null;

	if (contentType != null) {
	    return new MultipartFile() {
		@Override
		public void transferTo(File dest) throws IOException, IllegalStateException {
		}

		@Override
		public boolean isEmpty() {
		    return false;
		}

		@Override
		public long getSize() {
		    try {
			return Files.size(path);
		    } catch (IOException e) {
			e.printStackTrace();
			return 0;
		    }
		}

		@Override
		public String getOriginalFilename() {
		    return originalFileName;
		}

		@Override
		public String getName() {
		    return name;
		}

		@Override
		public InputStream getInputStream() throws IOException {
		    return Files.newInputStream(path);
		}

		@Override
		public String getContentType() {
		    return contentType;
		}

		@Override
		public byte[] getBytes() throws IOException {
		    return Files.readAllBytes(path);
		}
	    };
	}
	return null;
    }
}
