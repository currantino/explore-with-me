package ru.practicum.ewm.main.server.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.main.server.category.dto.create.CreateCategoryRequestDto;
import ru.practicum.ewm.main.server.category.dto.create.CreateCategoryResponseDto;
import ru.practicum.ewm.main.server.category.dto.patch.PatchCategoryRequestDto;
import ru.practicum.ewm.main.server.category.dto.patch.PatchCategoryResponseDto;
import ru.practicum.ewm.main.server.category.service.CategoryService;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/categories")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCategoryResponseDto createCategory(
            @Valid
            @RequestBody
            CreateCategoryRequestDto requestDto) {
        return categoryService.createCategory(requestDto);
    }

    @PatchMapping("/{catId}")
    public PatchCategoryResponseDto patchCategory(
            @PathVariable(name = "catId")
            Long catId,
            @Valid
            @RequestBody
            PatchCategoryRequestDto requestDto) {
        return categoryService.patchCategory(catId, requestDto);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<Void> removeCategory(
            @PathVariable
            Long catId
    ) {
        categoryService.removeCategory(catId);
        return ResponseEntity
                .noContent()
                .build();
    }

}
