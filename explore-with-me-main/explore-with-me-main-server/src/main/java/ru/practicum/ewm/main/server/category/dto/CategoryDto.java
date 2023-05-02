package ru.practicum.ewm.main.server.category.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link ru.practicum.ewm.main.server.category.entity.Category} entity
 */
@Getter
@Setter
public class CategoryDto implements Serializable {
    private Long id;
    private String name;
}