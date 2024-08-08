package com.amf.CarRegistry.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    void addUserImage(Integer id, MultipartFile file) throws IOException;
    String getUserImage(Integer id);
    String downloadCarsCSV();
    void uploadCarsCSV(MultipartFile file) throws IOException;
}