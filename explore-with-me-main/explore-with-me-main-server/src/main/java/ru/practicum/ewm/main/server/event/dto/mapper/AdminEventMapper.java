package ru.practicum.ewm.main.server.event.dto.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.main.server.event.dto.AdminEventDto;
import ru.practicum.ewm.main.server.event.entity.Event;
import ru.practicum.ewm.main.server.mapstruct.CentralMapperConfig;


@Mapper(config = CentralMapperConfig.class)
public interface AdminEventMapper {
    AdminEventDto toDto(Event event);

}