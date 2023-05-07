package ru.practicum.ewm.main.server.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.server.category.dto.CategoryDto;
import ru.practicum.ewm.main.server.category.service.CategoryService;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class PublicCategoryController {
    private final CategoryService categoryService;

    @ResponseStatus(OK)
    @GetMapping
    public List<CategoryDto> getAllCategories(
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
        return categoryService.getAllCategories(from, size);
    }

    @ResponseStatus(OK)
    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(
            @PathVariable(name = "catId")
            Long catId
    ) {
        return categoryService.getCategoryById(catId);
    }
}
