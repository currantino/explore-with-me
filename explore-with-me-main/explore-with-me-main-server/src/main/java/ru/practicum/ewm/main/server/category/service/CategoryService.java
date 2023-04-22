package ru.practicum.ewm.main.server.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.server.category.dto.create.CreateCategoryRequestDto;
import ru.practicum.ewm.main.server.category.dto.create.CreateCategoryResponseDto;
import ru.practicum.ewm.main.server.category.dto.patch.PatchCategoryRequestDto;
import ru.practicum.ewm.main.server.category.dto.patch.PatchCategoryResponseDto;
import ru.practicum.ewm.main.server.category.entity.Category;
import ru.practicum.ewm.main.server.category.mapper.CreateCategoryMapper;
import ru.practicum.ewm.main.server.category.mapper.PatchCategoryMapper;
import ru.practicum.ewm.main.server.category.repository.CategoryRepository;
import ru.practicum.ewm.main.server.exception.CategoryNotFoundException;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CreateCategoryMapper createCategoryMapper;
    private final PatchCategoryMapper patchCategoryMapper;

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
}
