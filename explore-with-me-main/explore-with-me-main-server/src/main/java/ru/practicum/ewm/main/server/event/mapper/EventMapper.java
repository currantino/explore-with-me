package ru.practicum.ewm.main.server.event.mapper;

import org.mapstruct.*;
import ru.practicum.ewm.main.server.config.mapstruct.CentralMapperConfig;
import ru.practicum.ewm.main.server.event.dto.CreateEventRequestDto;
import ru.practicum.ewm.main.server.event.dto.EventFullDto;
import ru.practicum.ewm.main.server.event.dto.EventShortDto;
import ru.practicum.ewm.main.server.event.dto.UpdateEventRequestDto;
import ru.practicum.ewm.main.server.event.entity.Event;
import ru.practicum.ewm.main.server.location.mapper.LocationMapper;

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