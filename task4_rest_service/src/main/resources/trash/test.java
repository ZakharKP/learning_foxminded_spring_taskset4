package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.util.CSVHelper;

public class test {
	public static void main(String[] args) {
		Set<String> make = new HashSet<>();
		Set<String> category = new HashSet<>();
		///main/resources/csv/file.csv
		Path path = Paths.get("src", "main", "resources", "csv", "file.csv");
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(Files.newInputStream(path), "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.builder().setHeader(CSVHelper.HEADERS).setSkipHeaderRecord(true)
						.setIgnoreHeaderCase(true).setTrim(true).build());) {
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				make.add(csvRecord.get("Make"));
				String cat = csvRecord.get("Category");
				cat = cat.replace("\"", "");
				for(String c : cat.split(",")) {
				category.add(c.trim());
				}
			}

		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
		
		
System.out.println();
		for(String cat : category) {
			System.out.println("'" + cat.toUpperCase() + "'" + ",");
		}
	}
}
