package ru.practicum.ewm.main.server.comment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import ru.practicum.ewm.main.server.validation.NotBlankOrNull;

import javax.validation.constraints.Size;

@Value
public class UpdateCommentDto {
    @Size(max = 2047, message = "Comment text length must be less than 2048 characters long.")
    @NotBlankOrNull(message = "Comment text must not be blank.")
    String text;

    @JsonCreator
    public UpdateCommentDto(
            @JsonProperty("text")
            String text
    ) {
        this.text = text;
    }
}
