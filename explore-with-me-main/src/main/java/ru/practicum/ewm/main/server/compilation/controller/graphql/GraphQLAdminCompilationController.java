package ru.practicum.ewm.main.server.compilation.controller.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import ru.practicum.ewm.main.server.compilation.dto.CompilationDto;
import ru.practicum.ewm.main.server.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.main.server.compilation.dto.UpdateCompilationRequestDto;
import ru.practicum.ewm.main.server.compilation.service.AdminCompilationService;

import javax.validation.Valid;

@Validated
@Controller
@RequiredArgsConstructor
public class GraphQLAdminCompilationController {
    private final AdminCompilationService adminCompilationService;

    @MutationMapping
    public CompilationDto createCompilation(
            @Argument
            @Valid
            NewCompilationDto compilation
    ) {
        return adminCompilationService.createCompilation(compilation);
    }

    @MutationMapping
    public void deleteCompilation(
            @Argument
            Long compilationId
    ) {
        adminCompilationService.deleteCompilationById(compilationId);
    }

    @MutationMapping
    public CompilationDto patchCompilation(
            @Argument
            Long compilationId,
            @Argument
            @Valid
            UpdateCompilationRequestDto compilation
    ) {
        return adminCompilationService.patchCompilation(compilationId, compilation);
    }
}
