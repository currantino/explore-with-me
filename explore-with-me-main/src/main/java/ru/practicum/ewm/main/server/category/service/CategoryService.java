package ru.practicum.ewm.main.server.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.server.category.dto.*;
import ru.practicum.ewm.main.server.category.entity.Category;
import ru.practicum.ewm.main.server.category.mapper.CategoryMapper;
import ru.practicum.ewm.main.server.category.mapper.CreateCategoryMapper;
import ru.practicum.ewm.main.server.category.mapper.PatchCategoryMapper;
import ru.practicum.ewm.main.server.category.repository.CategoryRepository;
import ru.practicum.ewm.main.server.exception.CategoryNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CreateCategoryMapper createCategoryMapper;
    private final PatchCategoryMapper patchCategoryMapper;
    private final CategoryMapper categoryMapper;

    public CreateCategoryResponseDto createCategory(CreateCategoryRequestDto requestDto) {
        Category category = createCategoryMapper.toEntity(requestDto);
        categoryRepository.save(category);
        return createCategoryMapper.toResponseDto(category);
    }

    public void removeCategory(Long catId) {
        categoryRepository.deleteById(catId);
    }


    public PatchCategoryResponseDto patchCategory(Long catId, PatchCategoryRequestDto requestDto) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new CategoryNotFoundException("Cannot find the requested category."));
        Category mapped = patchCategoryMapper.partialUpdate(requestDto, category);
        categoryRepository.save(mapped);
        return patchCategoryMapper.toResponseDto(mapped);
    }

    public List<CategoryDto> getAllCategories(Integer from, Integer size) {
        Pageable pageRequest = PageRequest.of(from, size);
        return categoryRepository
                .findAll(pageRequest)
                .map(categoryMapper::toDto)
                .getContent();
    }

    public CategoryDto getCategoryById(Long catId) {
        return categoryRepository
                .findById(catId)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new CategoryNotFoundException("Could not find the requested category."));
    }
}
