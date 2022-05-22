package org.example.mapstruct.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    private String idStr;
    private String make;
    private int seatCount;
    private String type;
}
