package com.amf.CarRegistry.controller;

import com.amf.CarRegistry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/{id}/addImage")
    public ResponseEntity<String> addUserImage(@PathVariable Integer id, @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            userService.addUserImage(id, imageFile);
            return ResponseEntity.ok("Image successfully saved.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to save image.");
        }
    }

    @GetMapping("/{id}/getImage")
    public ResponseEntity<String> getUserImage(@PathVariable Integer id) {
        try {
            String image = userService.getUserImage(id);
            return ResponseEntity.ok(image);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found.");
        }
    }

    @GetMapping("/cars/downloadCSV")
    public ResponseEntity<String> downloadCarsCSV() {
        try {
            String csv = userService.downloadCarsCSV();
            return ResponseEntity.ok(csv);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to download CSV.");
        }
    }

    @PostMapping("/cars/uploadCSV")
    public ResponseEntity<String> uploadCarsCSV(@RequestParam("file") MultipartFile file) {
        try {
            userService.uploadCarsCSV(file);
            return ResponseEntity.ok("CSV successfully uploaded.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to upload CSV.");
        }
    }
}