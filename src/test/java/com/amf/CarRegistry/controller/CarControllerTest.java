package com.amf.CarRegistry.controller;

import com.amf.CarRegistry.controller.dto.CarDTO;
import com.amf.CarRegistry.controller.mapper.CarMapper;
import com.amf.CarRegistry.repository.entity.Brand;
import com.amf.CarRegistry.repository.entity.Car;
import com.amf.CarRegistry.service.BrandService;
import com.amf.CarRegistry.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarControllerTest {

    @Mock
    private CarService carService;

    @Mock
    private BrandService brandService;

    @Mock
    private CarMapper carMapper;

    @InjectMocks
    private CarController carController;

    private Car car;
    private CarDTO carDTO;
    private Brand brand;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setId(1);
        car.setModel("Test");

        carDTO = new CarDTO();
        carDTO.setId(1);
        carDTO.setModel("Test DTO");

        brand = new Brand();
        brand.setId(1);
        brand.setName("Test Brand");
    }

    @Test
    void testGetCar() {
        when(carService.getCarById(1)).thenReturn(car);
        when(carMapper.toDto(car)).thenReturn(carDTO);

        ResponseEntity<?> response = carController.getCar(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(carDTO, response.getBody());
    }

    @Test
    void testGetCar_NotFound() {
        when(carService.getCarById(1)).thenReturn(null);

        ResponseEntity<?> response = carController.getCar(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAllCars() throws Exception {
        List<Car> carList = Arrays.asList(car);
        CompletableFuture<List<Car>> futureCarList = CompletableFuture.completedFuture(carList);
        when(carService.getAllCars()).thenReturn(futureCarList);
        when(carMapper.toDto(car)).thenReturn(carDTO);

        ResponseEntity<?> response = carController.getAllCars();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<CarDTO> responseBody = (List<CarDTO>) response.getBody();
        assertNotNull(responseBody);
        assertEquals(1, responseBody.size());
        assertEquals(carDTO, responseBody.get(0));
    }

    @Test
    void testAddCar() {
        when(brandService.getBrandById(1)).thenReturn(brand);
        when(carMapper.toDomain(any(CarDTO.class), any(Brand.class))).thenReturn(car);
        when(carService.addCar(any(Car.class))).thenReturn(car);

        ResponseEntity<?> response = carController.addCar(carDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testAddCar_BrandNotFound() {
        when(brandService.getBrandById(1)).thenReturn(null);

        ResponseEntity<?> response = carController.addCar(carDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testUpdateCar() {
        when(brandService.getBrandById(1)).thenReturn(brand);
        when(carMapper.toDomain(any(CarDTO.class), any(Brand.class))).thenReturn(car);
        when(carService.updateCar(anyInt(), any(Car.class))).thenReturn(car);
        when(carMapper.toDto(car)).thenReturn(carDTO);

        ResponseEntity<?> response = carController.updateCar(1, carDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(carDTO, response.getBody());
    }

    @Test
    void testUpdateCar_NotFound() {
        when(brandService.getBrandById(1)).thenReturn(brand);
        when(carMapper.toDomain(any(CarDTO.class), any(Brand.class))).thenReturn(car);
        when(carService.updateCar(anyInt(), any(Car.class))).thenReturn(null);

        ResponseEntity<?> response = carController.updateCar(1, carDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteCar() {
        when(carService.deleteCar(1)).thenReturn(true);

        ResponseEntity<?> response = carController.deleteCar(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteCar_NotFound() {
        when(carService.deleteCar(1)).thenReturn(false);

        ResponseEntity<?> response = carController.deleteCar(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
