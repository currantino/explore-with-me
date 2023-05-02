package ru.practicum.ewm.main.server.category.dto.mapper;

import org.mapstruct.Mapper;
import ru.practicum.ewm.main.server.category.dto.CategoryDto;
import ru.practicum.ewm.main.server.category.entity.Category;
import ru.practicum.ewm.main.server.mapstruct.CentralMapperConfig;

@Mapper(config = CentralMapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

}