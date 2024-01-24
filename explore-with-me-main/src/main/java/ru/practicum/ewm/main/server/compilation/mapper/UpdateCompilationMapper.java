package ru.practicum.ewm.main.server.compilation.mapper;

import org.mapstruct.*;
import ru.practicum.ewm.main.server.compilation.dto.UpdateCompilationRequestDto;
import ru.practicum.ewm.main.server.compilation.entity.Compilation;
import ru.practicum.ewm.main.server.config.mapstruct.CentralMapperConfig;
import ru.practicum.ewm.main.server.event.entity.Event;

@Mapper(config = CentralMapperConfig.class)
public interface UpdateCompilationMapper {
    @Mapping(target = "events", qualifiedByName = "idToEvent")
    Compilation toEntity(UpdateCompilationRequestDto updateCompilationRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "events", qualifiedByName = "idToEvent")
    Compilation partialUpdate(
            UpdateCompilationRequestDto updateCompilationRequestDto,
            @MappingTarget
            Compilation compilation
    );


    @Named("idToEvent")
    static Event idToEvent(Long id) {
        return Event.builder()
                .id(id)
                .build();
    }
}