package ru.practicum.ewm.main.server.category.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class PatchCategoryRequestDto {
    @NotBlank(message = "Category name must not be blank.")
    String name;

    @JsonCreator
    public PatchCategoryRequestDto(
            @JsonProperty("name")
            String name
    ) {
        this.name = name;
    }
}