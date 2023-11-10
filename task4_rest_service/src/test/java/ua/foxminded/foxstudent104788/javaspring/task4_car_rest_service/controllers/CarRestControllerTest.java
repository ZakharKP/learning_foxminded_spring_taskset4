package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.Car;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.CarDto;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.CarDtoFilter;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.models.Category;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.services.CarService;
import ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.testdata.ForTestDataCreator;

@WebMvcTest(CarController.class)
class CarRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarService service;

    @WithMockUser
    @Test
    void testGetCars() throws Exception {

	CarDtoFilter filter = ForTestDataCreator.getCarDtoFilter();
	Page<CarDto> carDtoPage = ForTestDataCreator.getConvertPageToPageCarDto(ForTestDataCreator.getPageCar(filter));
	when(service.getPageCarDtos(any(CarDtoFilter.class))).thenReturn(carDtoPage);

	mvc.perform(get("/api/cars").with(csrf())
		.param("sorting", filter.getSorting().name())
		.param("sorting_direction", filter.getDirection().name())
		.param("make", filter.getMake().name())
		.param("category", filter.getCategory().name())
		.param("year", String.valueOf(filter.getYear()))
		.param("searching", filter.getSearchArgument())
		.param("page_size", String.valueOf(filter.getPageSize()))
		.param("page", String.valueOf(filter.getPage()))
		.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	verify(service).getPageCarDtos(filter);
    }

    @WithMockUser
    @Test
    void testGetCar() throws Exception {

	Car car = ForTestDataCreator.getCar();
	when(service.getCarDto(anyString())).thenReturn(new CarDto(car));

	mvc.perform(get("/api/cars/{carId}", car.getId()).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());

	verify(service).getCarDto(car.getId());
    }

    @WithMockUser
    @Test
    void testCreateCar() throws Exception {

	CarDto carDto = new CarDto(ForTestDataCreator.getNewCar());
	when(service.createCar(any(CarDto.class))).thenReturn(carDto);

	mvc.perform(post("/api/cars/make/{make}/model/{model}/year/{year}",
		carDto.getMake(),
		carDto.getModel().toString(),
		carDto.getYear()).with(csrf())
		.contentType(MediaType.APPLICATION_JSON)
		.param("categories", carDto.getCategories().stream()
			.map(Category::toString).toArray(String[]::new)))
		.andExpect(status().isCreated());

	verify(service).createCar(carDto);
    }

    @WithMockUser
    @Test
    void testUpdateCar() throws Exception {

	CarDto carDto = new CarDto(ForTestDataCreator.getCar());
	when(service.updateCar(any(CarDto.class))).thenReturn(carDto);

	mvc.perform(put("/api/cars/id/{id}/make/{make}/model/{model}/year/{year}",
		carDto.getId(),
		carDto.getMake().toString(),
		carDto.getModel(),
		carDto.getYear()).with(csrf())
		.contentType(MediaType.APPLICATION_JSON)
		.param("categories", carDto.getCategories()
			.stream().map(Category::toString)
			.toArray(String[]::new)))
		.andExpect(status().isOk());

	verify(service).updateCar(carDto);
    }

    @WithMockUser
    @Test
    void testDeleteCar() throws Exception {
	
	String carId = "TEST1234";
	doNothing().when(service).deleteCar(anyString());

	mvc.perform(delete("/api/cars/{carId}", carId).with(csrf())
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());

	verify(service).deleteCar(carId);
    }

}
