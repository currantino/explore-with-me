package ru.practicum.ewm.main.server.event.mapper;

import org.mapstruct.*;
import ru.practicum.ewm.main.server.config.mapstruct.CentralMapperConfig;
import ru.practicum.ewm.main.server.event.dto.EventFullDto;
import ru.practicum.ewm.main.server.event.dto.UpdateEventAdminRequestDto;
import ru.practicum.ewm.main.server.event.entity.Event;
import ru.practicum.ewm.main.server.location.mapper.LocationMapper;


@Mapper(config = CentralMapperConfig.class, uses = {LocationMapper.class})
public interface AdminEventMapper {
    EventFullDto toDto(Event event);

    @Mapping(source = "categoryId", target = "category.id")
    Event toEntity(UpdateEventAdminRequestDto updateEventAdminRequestDto);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "categoryId", target = "category.id")
    Event partialUpdate(UpdateEventAdminRequestDto updateEventAdminRequestDto, @MappingTarget Event event);
}