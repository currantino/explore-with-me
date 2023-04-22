package ru.practicum.ewm.main.server.category.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.main.server.category.dto.create.CreateCategoryRequestDto;
import ru.practicum.ewm.main.server.category.dto.create.CreateCategoryResponseDto;
import ru.practicum.ewm.main.server.category.entity.Category;
import ru.practicum.ewm.main.server.mapstruct.CentralMapperConfig;

@Mapper(config = CentralMapperConfig.class)
public interface CreateCategoryMapper {
    CreateCategoryResponseDto toResponseDto(Category category);

    Category toEntity(CreateCategoryRequestDto dto);
}
