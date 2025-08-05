package com.example.StainlesSteel.Tables;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private String name;
    private Double price;
    private String size;
    private String color;
    private String description;
    private List<String> specifications;
}
