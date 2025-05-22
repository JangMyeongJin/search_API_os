package net.clush.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PropertiesResponseDTO {
    private String status;
    private String message;
    private int code;
} 