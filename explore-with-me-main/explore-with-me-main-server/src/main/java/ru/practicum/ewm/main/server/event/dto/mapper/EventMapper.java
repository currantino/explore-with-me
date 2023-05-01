package ru.practicum.ewm.main.server.event.dto.mapper;

import org.mapstruct.*;
import ru.practicum.ewm.main.server.event.controller.EventShortDto;
import ru.practicum.ewm.main.server.event.dto.CreateEventRequestDto;
import ru.practicum.ewm.main.server.event.dto.EventFullDto;
import ru.practicum.ewm.main.server.event.dto.UpdateEventRequestDto;
import ru.practicum.ewm.main.server.event.entity.Event;
import ru.practicum.ewm.main.server.location.dto.mapper.LocationMapper;
import ru.practicum.ewm.main.server.mapstruct.CentralMapperConfig;

@Mapper(config = CentralMapperConfig.class,
        uses = LocationMapper.class)
public interface EventMapper {
    @Mapping(target = "category.id", source = "category")
    Event toEntity(CreateEventRequestDto eventDto);

    EventFullDto toFullDto(Event event);

    EventShortDto toShortDto(Event event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "category.id", source = "category")
    void partialUpdate(
            UpdateEventRequestDto updateEventRequestDto,
            @MappingTarget
            Event event
    );
}