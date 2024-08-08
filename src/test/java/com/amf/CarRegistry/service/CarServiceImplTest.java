package com.amf.CarRegistry.service;

import com.amf.CarRegistry.repository.CarRepository;
import com.amf.CarRegistry.repository.entity.Car;
import com.amf.CarRegistry.service.impl.CarServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    @Test
    public void testGetCarById() {
        Car car = new Car();
        car.setId(1);
        when(carRepository.findById(1)).thenReturn(Optional.of(car));

        Car foundCar = carService.getCarById(1);
        assertNotNull(foundCar);
        assertEquals(1, foundCar.getId());
    }

    @Test
    public void testGetCarById_NotFound() {
        when(carRepository.findById(1)).thenReturn(Optional.empty());

        Car foundCar = carService.getCarById(1);
        assertNull(foundCar);
    }

    @Test
    public void testAddCar() {
        Car car = new Car();
        car.setModel("Test");
        when(carRepository.save(any(Car.class))).thenReturn(car);

        Car savedCar = carService.addCar(car);
        assertNotNull(savedCar);
        assertEquals("Test", savedCar.getModel());
    }

    @Test
    public void testUpdateCar() {
        Car car = new Car();
        car.setId(1);
        car.setModel("Updated");

        when(carRepository.existsById(1)).thenReturn(true);
        when(carRepository.save(any(Car.class))).thenReturn(car);

        Car updatedCar = carService.updateCar(1, car);
        assertNotNull(updatedCar);
        assertEquals(1, updatedCar.getId());
        assertEquals("Updated", updatedCar.getModel());
    }

    @Test
    public void testDeleteCar() {
        when(carRepository.existsById(1)).thenReturn(true);

        boolean isDeleted = carService.deleteCar(1);
        assertTrue(isDeleted);
        verify(carRepository, times(1)).deleteById(1);
    }

    @Test
    public void testGetAllCars() throws Exception {
        Car car1 = new Car();
        car1.setId(1);
        car1.setModel("Car 1");

        Car car2 = new Car();
        car2.setId(2);
        car2.setModel("Car 2");

        List<Car> cars = Arrays.asList(car1, car2);
        when(carRepository.findAll()).thenReturn(cars);

        CompletableFuture<List<Car>> futureCars = carService.getAllCars();
        List<Car> result = futureCars.get();
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
