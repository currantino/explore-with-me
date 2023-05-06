package ru.practicum.ewm.main.server.compilation.dto;

import lombok.Value;
import ru.practicum.ewm.main.server.event.dto.EventShortDto;

import java.util.List;

@Value
public class CompilationDto {
    Long id;
    String title;
    Boolean pinned;
    List<EventShortDto> events;
}