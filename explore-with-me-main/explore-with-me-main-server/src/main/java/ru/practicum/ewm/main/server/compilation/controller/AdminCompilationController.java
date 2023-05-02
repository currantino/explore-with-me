package ru.practicum.ewm.main.server.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.server.compilation.dto.CompilationDto;
import ru.practicum.ewm.main.server.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.main.server.compilation.service.AdminCompilationService;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@Validated
@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationController {
    private final AdminCompilationService adminCompilationService;

    @ResponseStatus(CREATED)
    @PostMapping
    public CompilationDto createCompilation(
            @Valid
            @RequestBody
            NewCompilationDto compilationDto
    ) {
        return adminCompilationService.createCompilation(compilationDto);
    }

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("/{compId}")
    public void deleteCompilationById(
            @PathVariable(name = "compId")
            Long compId
    ) {
        adminCompilationService.deleteCompilationById(compId);
    }

    @ResponseStatus(OK)
    @PatchMapping("/{compId}")
    public CompilationDto patchCompilation(
            @PathVariable(name = "compId")
            Long compId,
            @Valid
            @RequestBody
            UpdateCompilationRequestDto updateRequestDto
    ) {
        return adminCompilationService.patchCompilation(compId, updateRequestDto);
    }
}
