package ru.practicum.ewm.main.server.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.server.compilation.dto.CompilationDto;
import ru.practicum.ewm.main.server.compilation.service.PublicCompilationService;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class PublicCompilationController {
    private final PublicCompilationService compilationService;

    @ResponseStatus(OK)
    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(
            @PathVariable(name = "compId")
            Long compId
    ) {
        return compilationService.getCompilationById(compId);
    }

    @ResponseStatus(OK)
    @GetMapping
    public List<CompilationDto> getCompilations(
            @RequestParam(
                    name = "pinned",
                    defaultValue = "false"
            )
            Boolean pinned,
            @RequestParam(
                    name = "from",
                    defaultValue = "0"
            )
            Integer from,
            @RequestParam(
                    name = "size",
                    defaultValue = "10"
            )
            Integer size
    ) {
        return compilationService
                .getCompilations(pinned, from, size)
                .getContent();
    }
}
