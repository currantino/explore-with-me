package ru.practicum.ewm.main.server.location.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.ewm.main.server.config.mapstruct.CentralMapperConfig;
import ru.practicum.ewm.main.server.location.dto.LocationDto;
import ru.practicum.ewm.main.server.location.entity.Location;

@Mapper(config = CentralMapperConfig.class)
public interface LocationMapper {
    Location toEntity(LocationDto dto);

    LocationDto toDto(Location location);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Location partialUpdate(LocationDto locationDto,
                           @MappingTarget
                           Location location
    );
}
