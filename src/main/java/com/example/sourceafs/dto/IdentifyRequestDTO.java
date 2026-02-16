package com.example.sourceafs.dto;

import lombok.Data;

@Data
public class IdentifyRequestDTO {
    private String fingerprintBase64; // JSON field for probe fingerprint
}
