package com.amf.CarRegistry.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandDTO {
    private Integer id;
    private String name;
    private Integer warranty;
    private String country;
}
