package ru.practicum.ewm.main.server.compilation.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.practicum.ewm.main.server.compilation.dto.CompilationDto;
import ru.practicum.ewm.main.server.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.main.server.compilation.entity.Compilation;
import ru.practicum.ewm.main.server.event.dto.mapper.EventMapper;
import ru.practicum.ewm.main.server.event.entity.Event;
import ru.practicum.ewm.main.server.mapstruct.CentralMapperConfig;

@Mapper(config = CentralMapperConfig.class, uses = {EventMapper.class})
public interface CompilationMapper {
    CompilationDto toDto(Compilation compilation);

    @Mapping(target = "events", qualifiedByName = "idToEvent")
    Compilation toEntity(NewCompilationDto dto);

    @Named("idToEvent")
    static Event idToEvent(Long id) {
        return Event.builder()
                .id(id)
                .build();
    }
}