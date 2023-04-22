package ru.practicum.ewm.main.server.category.dto.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class CreateCategoryRequestDto {
    @NotBlank(message = "Category name must not be blank.")
    String name;

    @JsonCreator
    public CreateCategoryRequestDto(
            @JsonProperty("name")
            String name
    ) {
        this.name = name;
    }
}
