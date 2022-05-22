package org.example.mapstruct.mapper;


import org.example.mapstruct.dto.CarDto;
import org.example.mapstruct.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    @Mappings({
            @Mapping(source = "id", target = "idStr"),
            @Mapping(source = "numberOfSeats", target = "seatCount")
    })
    CarDto carToCarDto(Car car);
}
