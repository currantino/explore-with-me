package ru.practicum.ewm.main.server.comment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
public class UpdateCommentDto {
    @Size(max = 2047, message = "Comment text length must be less than 2048 characters long.")
    @NotBlank(message = "Comment text must not be blank or null.")
    String text;

    @JsonCreator
    public UpdateCommentDto(
            @JsonProperty("text")
            String text
    ) {
        this.text = text;
    }
}
