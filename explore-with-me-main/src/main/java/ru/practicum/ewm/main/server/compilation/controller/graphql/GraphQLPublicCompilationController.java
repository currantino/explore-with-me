package ru.practicum.ewm.main.server.compilation.controller.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import ru.practicum.ewm.main.server.compilation.dto.CompilationDto;
import ru.practicum.ewm.main.server.compilation.service.PublicCompilationService;

import java.util.List;

@Validated
@Controller
@RequiredArgsConstructor
public class GraphQLPublicCompilationController {
    private final PublicCompilationService compilationService;

    @QueryMapping
    public List<CompilationDto> compilationsPaged(
            @Argument
            Boolean pinned,
            @Argument
            Integer from,
            @Argument
            Integer size
    ) {
        return compilationService.getCompilations(pinned, from, size)
                .getContent();
    }
}
