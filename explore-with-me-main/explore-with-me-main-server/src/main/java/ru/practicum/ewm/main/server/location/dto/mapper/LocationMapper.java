package ru.practicum.ewm.main.server.location.dto.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.main.server.location.dto.LocationDto;
import ru.practicum.ewm.main.server.location.entity.Location;
import ru.practicum.ewm.main.server.mapstruct.CentralMapperConfig;

@Mapper(config = CentralMapperConfig.class)
public interface LocationMapper {
    Location toEntity(LocationDto dto);
}
