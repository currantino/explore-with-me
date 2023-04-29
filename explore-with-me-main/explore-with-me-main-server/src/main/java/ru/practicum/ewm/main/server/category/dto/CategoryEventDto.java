package ru.practicum.ewm.main.server.category.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link ru.practicum.ewm.main.server.category.entity.Category} entity
 */
@Data
public class CategoryEventDto implements Serializable {
    private final Long id;
    private final String name;
}