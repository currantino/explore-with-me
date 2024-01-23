package ru.practicum.ewm.main.server.category.controller.graphql;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.main.server.category.dto.CreateCategoryRequestDto;
import ru.practicum.ewm.main.server.category.dto.CreateCategoryResponseDto;
import ru.practicum.ewm.main.server.category.dto.PatchCategoryRequestDto;
import ru.practicum.ewm.main.server.category.dto.PatchCategoryResponseDto;
import ru.practicum.ewm.main.server.category.service.CategoryService;

import javax.validation.Valid;

@Validated
@Controller
@RequiredArgsConstructor
public class GraphQLAdminCategoryController {

    private final CategoryService categoryService;

    @MutationMapping
    public CreateCategoryResponseDto createCategory(
            @Argument("category")
            @Valid
            CreateCategoryRequestDto requestDto) {
        return categoryService.createCategory(requestDto);
    }

    @MutationMapping
    public PatchCategoryResponseDto patchCategory(
            @Argument
            Long catId,
            @Argument("category")
            @Valid
            PatchCategoryRequestDto requestDto) {
        return categoryService.patchCategory(catId, requestDto);
    }

    @MutationMapping
    public ResponseEntity<Void> removeCategory(
            @Argument
            Long catId
    ) {
        categoryService.removeCategory(catId);
        return ResponseEntity
                .noContent()
                .build();
    }

}
