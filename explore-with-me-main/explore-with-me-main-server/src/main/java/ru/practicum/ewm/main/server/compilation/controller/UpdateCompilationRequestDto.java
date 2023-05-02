package ru.practicum.ewm.main.server.compilation.controller;

import lombok.Value;
import ru.practicum.ewm.main.server.validation.NotBlankOrNull;

import java.util.List;


@Value
public class UpdateCompilationRequestDto {
    @NotBlankOrNull
    String title;
    Boolean pinned;
    List<Long> events;
}