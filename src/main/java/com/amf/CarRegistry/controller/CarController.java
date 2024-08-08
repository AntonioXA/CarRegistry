package com.amf.CarRegistry.controller;

import com.amf.CarRegistry.controller.dto.CarDTO;
import com.amf.CarRegistry.controller.mapper.CarMapper;
import com.amf.CarRegistry.repository.entity.Brand;
import com.amf.CarRegistry.service.BrandService;
import com.amf.CarRegistry.service.CarService;
import com.amf.CarRegistry.repository.entity.Car;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.concurrent.ExecutionException;


@RestController
@Slf4j
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CarMapper carMapper;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getCar(@PathVariable Integer id) {
        log.info("Retrieving Car info for ID: " + id);
        Car car = carService.getCarById(id);
        if (car == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(carMapper.toDto(car), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCars() {
        CompletableFuture<List<Car>> carsFuture = carService.getAllCars();
        List<Car> cars;
        try {
            cars = carsFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error retrieving cars", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<CarDTO> carDTOs = cars.stream().map(carMapper::toDto).collect(Collectors.toList());
        return new ResponseEntity<>(carDTOs, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCar(@RequestBody CarDTO carDTO) {
        log.info("Adding a new Car");
        try {
            Brand brand = brandService.getBrandById(carDTO.getBrandId());
            if (brand == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Car car = carMapper.toDomain(carDTO, brand);
            carService.addCar(car);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error adding car", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateCar(@PathVariable Integer id, @RequestBody CarDTO carDTO) {
        log.info("Updating Car info for ID: " + id);
        try {
            Brand brand = brandService.getBrandById(carDTO.getBrandId());
            if (brand == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Car car = carMapper.toDomain(carDTO, brand);
            Car updatedCar = carService.updateCar(id, car);
            if (updatedCar == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(carMapper.toDto(updatedCar), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating car", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable Integer id) {
        log.info("Deleting Car with ID: " + id);
        boolean isDeleted = carService.deleteCar(id);
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
