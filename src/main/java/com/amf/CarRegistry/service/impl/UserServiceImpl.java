package com.amf.CarRegistry.service.impl;

import com.amf.CarRegistry.repository.BrandRepository;
import com.amf.CarRegistry.repository.CarRepository;
import com.amf.CarRegistry.repository.UserRepository;
import com.amf.CarRegistry.repository.entity.Brand;
import com.amf.CarRegistry.repository.entity.Car;
import com.amf.CarRegistry.repository.entity.User;
import com.amf.CarRegistry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public void addUserImage(Integer id, MultipartFile file) throws IOException {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        userRepository.save(user);
    }

    @Override
    public String getUserImage(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getImage();
    }

    @Override
    public String downloadCarsCSV() {
        List<Car> cars = carRepository.findAll();
        StringBuilder csvBuilder = new StringBuilder("id,brand,model,year,price\n");

        for (Car car : cars) {
            csvBuilder.append(car.getId()).append(",")
                    .append(car.getBrand().getName()).append(",")
                    .append(car.getModel()).append(",")
                    .append(car.getYear()).append(",")
                    .append(car.getPrice()).append("\n");
        }

        return csvBuilder.toString();
    }

    @Override
    public void uploadCarsCSV(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Car car = new Car();
                Optional<Brand> brand = brandRepository.findByName(data[1]);
                if (brand.isEmpty()) {
                    throw new RuntimeException("Brand not found: " + data[1]);
                }
                car.setBrand(brand.get());
                car.setModel(data[2]);
                car.setYear(Integer.parseInt(data[3]));
                car.setPrice(Double.parseDouble(data[4]));
                carRepository.save(car);
            }
        }
    }
}