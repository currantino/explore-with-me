package ru.practicum.ewm.main.server.compilation.dto;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Value
public class NewCompilationDto {
    @NotBlank(message = "Compilation title must not be blank.")
    String title;
    Boolean pinned;
    List<Long> events;
}