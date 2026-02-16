package com.example.sourceafs.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PersonResponseDTO {
    private Long id;
    private String name;
}
