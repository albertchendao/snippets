package org.example.mapstruct.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.mapstruct._enum.CarType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private Integer id;
    private String make;
    private int numberOfSeats;
    private CarType type;
}
