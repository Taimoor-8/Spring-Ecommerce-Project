package com.example.ecommerceproject.dto;

import lombok.Data;

@Data
public class ProductDTO {

    private long id;
    private String name;
    private String description;
    private double price;
    private double weight;
    private String imageName;
    private int CategoryId;
}
