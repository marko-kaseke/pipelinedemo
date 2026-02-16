package com.example.sourceafs.dto;

import lombok.Data;

@Data
public class PersonRequestDTO {
    private String name;
    private String fingerprintBase64; // image encoded as base64
}
