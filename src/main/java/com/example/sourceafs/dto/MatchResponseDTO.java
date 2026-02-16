package com.example.sourceafs.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MatchResponseDTO {
    private boolean match;
    private String matchedPersonName;
    private double similarity;
}
