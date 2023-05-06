package ru.practicum.ewm.main.server.compilation.mapper;

import org.mapstruct.*;
import ru.practicum.ewm.main.server.compilation.dto.CompilationDto;
import ru.practicum.ewm.main.server.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.main.server.compilation.entity.Compilation;
import ru.practicum.ewm.main.server.config.mapstruct.CentralMapperConfig;
import ru.practicum.ewm.main.server.event.entity.Event;
import ru.practicum.ewm.main.server.event.mapper.EventMapper;

@Mapper(config = CentralMapperConfig.class, uses = {EventMapper.class})
public interface CompilationMapper {
    CompilationDto toDto(Compilation compilation);

    @Mapping(target = "events", qualifiedByName = "idToEvent")
    Compilation toEntity(NewCompilationDto dto);

    @Named("idToEvent")
    default Event idToEvent(Long id) {
        return Event.builder()
                .id(id)
                .build();
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Compilation partialUpdate(CompilationDto compilationDto, @MappingTarget Compilation compilation);

}