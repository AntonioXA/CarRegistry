package com.amf.CarRegistry.controller;


import com.amf.CarRegistry.controller.dto.BrandDTO;
import com.amf.CarRegistry.controller.mapper.BrandMapper;
import com.amf.CarRegistry.repository.entity.Brand;
import com.amf.CarRegistry.service.BrandService;
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
public class BrandControllerTest {

    @Mock
    private BrandService brandService;

    @Mock
    private BrandMapper brandMapper;

    @InjectMocks
    private BrandController brandController;

    private Brand brand;
    private BrandDTO brandDTO;

    @BeforeEach
    void setUp() {
        brand = new Brand();
        brand.setId(1);
        brand.setName("Test Brand");

        brandDTO = new BrandDTO();
        brandDTO.setId(1);
        brandDTO.setName("Test Brand DTO");
    }

    @Test
    void testGetBrand() {
        when(brandService.getBrandById(1)).thenReturn(brand);
        when(brandMapper.toDto(brand)).thenReturn(brandDTO);

        ResponseEntity<?> response = brandController.getBrand(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(brandDTO, response.getBody());
    }

    @Test
    void testGetBrand_NotFound() {
        when(brandService.getBrandById(1)).thenReturn(null);

        ResponseEntity<?> response = brandController.getBrand(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAllBrands() throws Exception {
        List<Brand> brandList = Arrays.asList(brand);
        CompletableFuture<List<Brand>> futureBrandList = CompletableFuture.completedFuture(brandList);
        when(brandService.getAllBrands()).thenReturn(futureBrandList);
        when(brandMapper.toDto(brand)).thenReturn(brandDTO);

        ResponseEntity<?> response = brandController.getAllBrands();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<BrandDTO> responseBody = (List<BrandDTO>) response.getBody();
        assertNotNull(responseBody);
        assertEquals(1, responseBody.size());
        assertEquals(brandDTO, responseBody.get(0));
    }

    @Test
    void testAddBrand() {
        when(brandMapper.toDomain(any(BrandDTO.class))).thenReturn(brand);
        when(brandService.addBrand(any(Brand.class))).thenReturn(brand);

        ResponseEntity<?> response = brandController.addBrand(brandDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testUpdateBrand() {
        when(brandMapper.toDomain(any(BrandDTO.class))).thenReturn(brand);
        when(brandService.updateBrand(anyInt(), any(Brand.class))).thenReturn(brand);
        when(brandMapper.toDto(brand)).thenReturn(brandDTO);

        ResponseEntity<?> response = brandController.updateBrand(1, brandDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(brandDTO, response.getBody());
    }

    @Test
    void testUpdateBrand_NotFound() {
        when(brandMapper.toDomain(any(BrandDTO.class))).thenReturn(brand);
        when(brandService.updateBrand(anyInt(), any(Brand.class))).thenReturn(null);

        ResponseEntity<?> response = brandController.updateBrand(1, brandDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteBrand() {
        when(brandService.deleteBrand(1)).thenReturn(true);

        ResponseEntity<?> response = brandController.deleteBrand(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteBrand_NotFound() {
        when(brandService.deleteBrand(1)).thenReturn(false);

        ResponseEntity<?> response = brandController.deleteBrand(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
